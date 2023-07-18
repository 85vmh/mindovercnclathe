package com.mindovercnc.model

import okio.Path

val Path.extension: String
  get() = segments.last().substringAfterLast('.', "")
