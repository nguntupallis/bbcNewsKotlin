package com.bbcnews.config

import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption
import io.appium.java_client.ios.options.wda.SupportsAutoAcceptAlertsOption
import java.io.File
import java.util.Properties

object ConfigFactory {

    fun getConfig(device: String): Config {
        // Load the config file based on the platform version
        val configFile = when (device) {
            "Pixel 6" -> "src/main/kotlin/com/bbcnews/config/config_pixel6_emulator.properties"
            "Nexus 5" -> "src/main/kotlin/com/bbcnews/config/config_nexus5_emulator.properties"
            else -> throw IllegalArgumentException("Unknown device version: $device")
        }
        val inputStream = File(configFile).inputStream()

        // Parse the config properties and return them as a Config object
        val properties = Properties().run {
            load(inputStream)
            this
        }

        return Config(
            appPackage = properties.getProperty("app.package"),
            deviceName = properties.getProperty("device.name"),
            platformVersion = properties.getProperty("platform.version"),
            appPath = properties.getProperty("app.path"),
            appiumServerUrl = properties.getProperty("appium.server.url"),
            defaultWaitTimeSeconds = properties.getProperty("default.wait.time.seconds").toInt(),
            testUsername = properties.getProperty("test.username"),
            testPassword = properties.getProperty("test.password"),
            logLevel = properties.getProperty("log.level"),
            udid = properties.getProperty("udid"),
            appiumnoReset = properties.getProperty("appium.noReset"),
            autoGrantPermissionsOption = properties.getProperty("autoGrantPermissionsOption"),
            autoAcceptAlertsOption = properties.getProperty("autoAcceptAlertsOption")
        )
    }

    data class Config(
        val appPackage: String,
        val deviceName: String,
        val platformVersion: String,
        val appPath: String,
        val appiumServerUrl: String,
        val defaultWaitTimeSeconds: Int,
        val testUsername: String,
        val testPassword: String,
        val logLevel: String,
        val udid: String,
        val appiumnoReset: String,
        val autoGrantPermissionsOption: String,
        val autoAcceptAlertsOption: String
    )
}