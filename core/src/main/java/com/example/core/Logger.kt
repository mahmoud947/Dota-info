package com.example.core

class Logger(
    private val tag: String,
    private val isDebug: Boolean
) {

    fun log(msg: String) {
        if (!isDebug) {
            //production logging Crashlytics
        } else {
            printLogD(tag, msg)
        }
    }

    private fun printLogD(tag: String, msg: String) {
        println("$tag: $msg")
    }

    companion object Factory {
        fun buildDebug(tag: String): Logger {
            return Logger(tag = tag, isDebug = true)
        }

        fun buildRelease(tag: String): Logger {
            return Logger(tag = tag, isDebug = false)
        }
    }

}