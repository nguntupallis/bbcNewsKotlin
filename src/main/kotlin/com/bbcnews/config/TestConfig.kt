import com.bbcnews.config.ConfigFactory

object TestConfig {
    private val config = ConfigFactory.getConfig("Pixel 6")
    val appPackage = config.appPackage
    val deviceName = config.deviceName
    val platformVersion = config.platformVersion
    val appPath = config.appPath
    val appiumServerUrl = config.appiumServerUrl
    val defaultWaitTimeSeconds = config.defaultWaitTimeSeconds
    val testUsername = config.testUsername
    val testPassword = config.testPassword
    val logLevel = config.logLevel
    val appiumnoReset = config.appiumnoReset
    val autoGrantPermissionsOption = config.autoAcceptAlertsOption
    val autoAcceptAlertsOption = config.autoAcceptAlertsOption
}
