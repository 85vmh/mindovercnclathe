package com.mindovercnc.linuxcnc

import com.mindovercnc.dispatchers.NewSingleThreadDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.linuxcnc.parsing.CncStatusFactory
import com.mindovercnc.repository.CncStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import ro.dragossusi.proto.linuxcnc.CncStatus
import ro.dragossusi.proto.linuxcnc.LinuxCncGrpc
import ro.dragossusi.proto.linuxcnc.readStatusRequest

/** Implementation for [CncStatusRepository]. */
class CncStatusRepositoryImpl
constructor(
  newSingleThreadDispatcher: NewSingleThreadDispatcher,
  private val cncStatusFactory: CncStatusFactory,
  private val linuxCncGrpc: LinuxCncGrpc.LinuxCncBlockingStub
) : CncStatusRepository {

  private val logger = KotlinLogging.logger("CncStatusRepositoryImpl")

  private val scope = newSingleThreadDispatcher.createScope()

  @Deprecated("will be replaced by grpc") private val statusReader: StatusReader = StatusReader()

  @Deprecated("will be replaced by grpc", level = DeprecationLevel.ERROR)
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
        logger.error(e) { "Failed reading status" }
      }
      delay(100L)
    }
  }

  override val cncStatusFlow: Flow<CncStatus> = grpcStatusFlow
}
