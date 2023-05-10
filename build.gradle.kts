plugins {
    jacoco
    java
    kotlin("jvm") version "1.5.0"
    application
}

configurations {}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("junit:junit:4.13.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    runtimeOnly("com.android.tools:common:25.3.0")
    testImplementation("junit:junit:4.13.1")
    implementation("io.appium:java-client:8.4.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.2.1")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.14.1")
    testImplementation("io.cucumber:cucumber-java:6.11.0")
    testImplementation("io.cucumber:cucumber-junit:6.11.0")
}

tasks.register<JavaExec>("cucumber") {
    dependsOn("assemble", "compileTestJava")

    mainClass.set("io.cucumber.core.cli.Main")
    classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
    args = listOf("--plugin", "pretty", "--glue", "com.bbcnews.steps", "src/test/resources", "--tags", "@video")

    doFirst {
        // Configure jacoco agent for the test coverage.
        val jacocoAgent = zipTree(configurations.jacocoAgent.get().singleFile)
            .filter { it.name == "jacocoagent.jar" }
            .singleFile
        jvmArgs = listOf("-javaagent:$jacocoAgent=destfile=$buildDir/results/jacoco/cucumber.exec,append=false")
    }
}

tasks {
    named<JacocoReport>("jacocoTestReport") {
        // Give jacoco the file generated with the cucumber tests for the coverage.
        executionData(files("$buildDir/jacoco/test.exec", "$buildDir/results/jacoco/cucumber.exec"))
        reports {
            xml.required.set(true)
        }
    }

    named<Test>("test") {
        useJUnitPlatform()
    }
}
