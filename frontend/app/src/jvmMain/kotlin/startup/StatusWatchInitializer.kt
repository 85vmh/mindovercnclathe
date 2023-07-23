package startup

import StatusWatcher
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import initializer.InitializerStep
import mu.KotlinLogging

class StatusWatchInitializer(
    private val statusWatcher: StatusWatcher,
    private val ioDispatcher: IoDispatcher
) : InitializerStep {
    override suspend fun initialise() {
        LOG.info { "Starting status watcher" }
        statusWatcher.launchIn(ioDispatcher.createScope())
    }

    companion object {
        private val LOG = KotlinLogging.logger("StatusWatchInitializer")
    }
}