package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.NewSingleThreadDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.CncStatus
import ro.dragossusi.proto.linuxcnc.LinuxCncClient
import ro.dragossusi.proto.linuxcnc.ReadStatusRequest

/** Implementation for [CncStatusRepository]. */
class CncStatusRepositoryImpl
constructor(
    newSingleThreadDispatcher: NewSingleThreadDispatcher, private val linuxCncGrpc: LinuxCncClient
) : CncStatusRepository {

    private val logger = KotlinLogging.logger("CncStatusRepositoryImpl")

    private val scope = newSingleThreadDispatcher.createScope()

    private val grpcStatusFlow = flow {
        while (true) {
            val request = ReadStatusRequest()
            try {
                val status = linuxCncGrpc.ReadStatus().execute(request)
                emit(status)
            } catch (e: Exception) {
                logger.error(e) { "Failed reading status" }
            }
            delay(100L)
        }
    }

    override val cncStatusFlow: Flow<CncStatus> = grpcStatusFlow
}
