package codegen

sealed class FeedRateMode {
    data class UnitsPerMinute(val value: Double) : FeedRateMode()
    data class UnitsPerRevolution(val value: Double) : FeedRateMode()
}