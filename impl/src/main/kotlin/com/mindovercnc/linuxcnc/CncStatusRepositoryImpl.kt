package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.parsing.CncStatusFactory
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.CncStatus
import ro.dragossusi.proto.linuxcnc.LinuxCncGrpc
import ro.dragossusi.proto.linuxcnc.readStatusRequest

private val LOG = KotlinLogging.logger("CncStatusRepositoryImpl")

/** Implementation for [CncStatusRepository]. */
class CncStatusRepositoryImpl
constructor(
  ioDispatcher: IoDispatcher,
  private val cncStatusFactory: CncStatusFactory,
  private val linuxCncGrpc: LinuxCncGrpc.LinuxCncBlockingStub
) : CncStatusRepository {

  private val scope = ioDispatcher.createScope()

  @Deprecated("will be replaced by grpc") private val statusReader: StatusReader = StatusReader()

  @Deprecated("will be replaced by grpc")
  private val jniStatusFlow =
    statusReader
      .refresh(100L)
      .filterNotNull()
      .map { cncStatusFactory.parse(it) }
      .shareIn(scope, SharingStarted.Lazily, replay = 1)

  private val grpcStatusFlow = flow {
    while (true) {
      val request = readStatusRequest {}
      try {
        val status = linuxCncGrpc.readStatus(request)
        emit(status)
      } catch (e: Exception) {
        LOG.error(e) { "Failed reading status" }
      }
      delay(100L)
    }
  }

  override fun cncStatusFlow(): Flow<CncStatus> {
    return grpcStatusFlow
  }
}
