package com.bbcnews.utils

import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import org.apache.logging.log4j.LogManager
import java.io.File

object AppiumServer {
    private val log = LogManager.getLogger(AppiumServer::class.java)

    var appiumService: AppiumDriverLocalService? = null

    fun getNodePath(): String? {
        val command = "which node"
        val process = Runtime.getRuntime().exec(command)
        process.waitFor()
        val output = process.inputStream.bufferedReader().readLine()
        return if (output.isNullOrEmpty()) {
            null
        } else {
            output.trim()
        }
    }

    fun getAppiumPath(): String? {
        val command = "which appium"
        val process = Runtime.getRuntime().exec(command)
        process.waitFor()
        val output = process.inputStream.bufferedReader().readLine()
        return if (output.isNullOrEmpty()) {
            null
        } else {
            output.trim()
        }
    }

    fun startServer() {
        if (appiumService == null || !appiumService!!.isRunning) {
            val builder = AppiumServiceBuilder().withIPAddress("127.0.0.1")
                .usingPort(4723)
            builder.usingDriverExecutable(File(getNodePath())).build()
            builder.withAppiumJS(File(getAppiumPath())).build()

            val environment: HashMap<String, String> = HashMap()
            environment["PATH"] = "/usr/local/bin:${System.getenv("PATH")}"
            builder.withEnvironment(environment).build()
            builder.withArgument({ "--base-path" }, "/wd/hub")
            appiumService = AppiumDriverLocalService.buildService(builder)
            appiumService!!.start()
            if (appiumService!!.isRunning()) {
                System.out.println("Appium service started..." + appiumService)
            } else {
                System.out.println("Appium service failed.....")
                System.exit(0)
            }
            log.info("Appium server started on URL: ${appiumService!!.url}")
        } else {
            log.info("Appium server is already running on URL: ${appiumService!!.url}")
        }
    }

    fun stopServer() {
        if (appiumService != null && appiumService!!.isRunning) {
            appiumService!!.stop()
            log.info("Appium server stopped")
        } else {
            log.info("Appium server is not running")
        }
    }
}
