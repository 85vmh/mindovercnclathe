package com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert

import com.mindovercnc.model.FeedsAndSpeeds
import com.mindovercnc.model.MaterialCategory

internal object DummyData {
    val feedsAndSpeeds =
        listOf(
            FeedsAndSpeeds(
                "Steel",
                MaterialCategory.P,
                0.2..2.0,
                0.1..0.3,
                100..200,
            ),
            FeedsAndSpeeds(
                "Delrin",
                MaterialCategory.N,
                0.2..2.0,
                0.1..0.3,
                100..200,
            ),
            FeedsAndSpeeds(
                "Aluminium",
                MaterialCategory.N,
                0.2..2.0,
                0.1..0.3,
                100..200,
            ),
            FeedsAndSpeeds(
                "Cast Iron",
                MaterialCategory.K,
                0.2..2.0,
                0.1..0.3,
                100..200,
            ),
            FeedsAndSpeeds(
                "304 Stainless",
                MaterialCategory.M,
                0.2..2.0,
                0.1..0.3,
                100..200,
            ),
            FeedsAndSpeeds(
                "306 Stainless",
                MaterialCategory.M,
                0.2..2.0,
                0.1..0.3,
                100..200,
            ),
        )
}
