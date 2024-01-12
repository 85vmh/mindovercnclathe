package com.mindovercnc.log

enum class PrintColor(
    val color: String
) {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m");
}

fun String.colored(color: PrintColor) = "${color.color}$this${PrintColor.RESET.color}"