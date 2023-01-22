package ro.dragossusi.proto.linuxcnc

import ro.dragossusi.proto.linuxcnc.status.TaskStatus

private const val SETTING_FEED_RATE_INDEX = 1
private const val SETTING_SPINDLE_SPEED_INDEX = 2

/** The set feed rate, obtained from active settings */
val TaskStatus.setFeedRate: Double?
  get() = activeSettingsList[SETTING_FEED_RATE_INDEX]

/** The set spindle speed, obtained from active settings */
val TaskStatus.setSpindleSpeed: Double?
  get() = activeSettingsList[SETTING_SPINDLE_SPEED_INDEX]
