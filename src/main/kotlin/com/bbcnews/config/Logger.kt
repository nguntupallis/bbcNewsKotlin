package com.bbcnews.config

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Logger {
    companion object {
        private val logger: Logger = LogManager.getLogger(Logger::class.java)

        fun info(message: String) {
            logger.info(message)
        }

        fun warn(message: String) {
            logger.warn(message)
        }

        fun error(message: String) {
            logger.error(message)
        }
    }
}
