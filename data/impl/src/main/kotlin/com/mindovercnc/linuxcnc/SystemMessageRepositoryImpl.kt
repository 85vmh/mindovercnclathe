package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.NewSingleThreadDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.repository.SystemMessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.LinuxCncGrpc
import ro.dragossusi.proto.linuxcnc.readErrorRequest
import ro.dragossusi.proto.linuxcnc.status.SystemMessage

/** Implementation for [SystemMessageRepository]. */
class SystemMessageRepositoryImpl
constructor(
  private val linuxCncGrpc: LinuxCncGrpc.LinuxCncBlockingStub,
  newSingleThreadDispatcher: NewSingleThreadDispatcher
) : SystemMessageRepository {

  private val logger = KotlinLogging.logger("SystemMessageRepositoryImpl")

  private val scope = newSingleThreadDispatcher.createScope()

  override val systemMessageFlow: Flow<SystemMessage>
    get() =
      flow {
          while (true) {
            val request =
              readErrorRequest {
                // no-op
              }
            try {
              val systemMessage = linuxCncGrpc.readError(request)
              emit(systemMessage)
            } catch (e: Exception) {
              logger.error(e) { "Failed to get error" }
            }
            delay(100L)
          }
        }
        .shareIn(scope, started = SharingStarted.Lazily, replay = 1)
}
