package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.CncStatusRepository
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
    private val linuxCncGrpc: LinuxCncClient
) : CncStatusRepository {

    private val logger = KotlinLogging.logger("CncStatusRepositoryImpl")

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