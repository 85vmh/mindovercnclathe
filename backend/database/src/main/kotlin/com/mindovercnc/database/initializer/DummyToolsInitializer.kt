package com.mindovercnc.database.initializer

import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.entity.ToolHolderEntity
import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.database.table.CuttingInsertTable
import com.mindovercnc.database.table.MaterialCategory
import com.mindovercnc.model.*
import com.mindovercnc.model.tool.ToolHolderType
import com.mindovercnc.model.tool.ToolType
import initializer.InitializerStep
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.random.Random

internal class DummyToolsInitializer(
    private val count: Int
) : InitializerStep {
    override suspend fun initialise() {
        transaction {
            if (CuttingInsertEntity.count() == 0L) {
                createDummyInserts()
            }
            if (LatheToolEntity.count() == 0L) {
                createDummyTools()
            }
            if (ToolHolderEntity.count() == 0L) {
                createDummyHolders()
            }
            if (WorkpieceMaterialEntity.count() == 0L) {
                createWorkpieceMaterials()
            }
        }
    }

    private fun createDummyHolders() {
        val types = ToolHolderType.entries
        repeat(count) {
            ToolHolderEntity.new {
                holderNumber = it + 1
                holderType = types.random()
                cutter = null
                clampingPosition = 0
                xOffset = Random.nextDouble()
                zOffset = Random.nextDouble()
            }
        }
    }

    private fun createDummyTools() {
        LatheToolEntity.new {
            insert = getInsertByCode("DCMT")
            type = ToolType.Turning
            tipOrientation = TipOrientation.Position2.orient
            frontAngle = 0.0
            backAngle = 0.0
            spindleDirection = SpindleDirection.Reverse
        }
        LatheToolEntity.new {
            insert = getInsertByCode("VCMT")
            type = ToolType.Turning
            tipOrientation = TipOrientation.Position2.orient
            frontAngle = 0.0
            backAngle = 0.0
            spindleDirection = SpindleDirection.Reverse
        }
        LatheToolEntity.new {
            insert = getInsertByCode("CCMT")
            type = ToolType.Boring
            tipOrientation = TipOrientation.Position3.orient
            frontAngle = 0.0
            backAngle = 0.0
            spindleDirection = SpindleDirection.Reverse
            minBoreDiameter = 20.0
            maxZDepth = 50.0
        }
        LatheToolEntity.new {
            insert = getInsertByCode("VCMT")
            tipOrientation = TipOrientation.Position7.orient
            spindleDirection = SpindleDirection.Reverse
            type = ToolType.Parting
            bladeWidth = 2.0
            maxXDepth = 20.0
        }
        LatheToolEntity.new {
            type = ToolType.Drilling
            tipOrientation = TipOrientation.Position7.orient
            spindleDirection = SpindleDirection.Reverse
            toolDiameter = 8.0
            maxZDepth = 80.0
        }
    }

    private fun getInsertByCode(code: String): CuttingInsertEntity {
        return CuttingInsertEntity.find { CuttingInsertTable.code eq code }.first()
    }

    private fun createDummyInserts() {
        CuttingInsertEntity.new {
            madeOf = MadeOf.Carbide
            code = "CCMT"
            tipRadius = 0.8
            tipAngle = 80.0
            size = 9.5
        }
        CuttingInsertEntity.new {
            madeOf = MadeOf.Carbide
            code = "DCMT"
            tipRadius = 0.8
            tipAngle = 55.0
            size = 9.5
        }
        CuttingInsertEntity.new {
            madeOf = MadeOf.Carbide
            code = "VCMT"
            tipRadius = 0.8
            tipAngle = 35.0
            size = 9.5
        }
        CuttingInsertEntity.new {
            madeOf = MadeOf.Carbide
            code = "TCMT"
            tipRadius = 0.8
            tipAngle = 60.0
            size = 13.0
        }
    }

    private fun createWorkpieceMaterials() {
        WorkpieceMaterialEntity.new {
            this.name = "Steel"
            this.category = MaterialCategory.P.name
        }
        WorkpieceMaterialEntity.new {
            this.name = "Stainless Steel"
            this.category = MaterialCategory.M.name
        }
        WorkpieceMaterialEntity.new {
            this.name = "Cast Iron"
            this.category = MaterialCategory.K.name
        }
        WorkpieceMaterialEntity.new {
            this.name = "Non Ferrous"
            this.category = MaterialCategory.N.name
        }
        WorkpieceMaterialEntity.new {
            this.name = "Super Alloy"
            this.category = MaterialCategory.S.name
        }
        WorkpieceMaterialEntity.new {
            this.name = "Super Alloy"
            this.category = MaterialCategory.S.name
        }
    }
}