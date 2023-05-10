# BBC News Kotlin Appium tests
This is a sample test framework to run Kotlin appium tests on Android device

## Prerequisites

Before running the project, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 11
- Gradle build tool(version 8.1.1)
- Android SDK(version 34.0.1)
- Android Emulator(version 32.1.12)
- Docker(version 4.19.0 (106363))
  - Instructions for installing Docker can be found [here](https://docs.docker.com/engine/install/).
- Android Virtual Device (AVD) with Pixel 6
  - Create an AVD with the Pixel 6 device configuration using the Android Virtual Device Manager.
  - Configure the AVD to match the desired specifications for your tests.

## Setup Instructions

Follow these steps to set up and run the project:

1. Clone the repository:

   ```bash
   git clone git@github.com:nguntupallis/bbcNewsKotlin.git
   ```

2. Build and run the project using Gradle: Navigate to the project directory and execute the following command to build and run the tests:

    ```bash
    ./gradlew cucumber
    ```
    This command will compile the code, install the necessary dependencies, and run the tests using Cucumber on the configured AVD.
    
    Note: Again, please ensure that you have set up the environment variables and prerequisites correctly based on the documentation and installation guides for each tool.

3. Docker configuration (Work in Progress):

    The Dockerfile provided in the repository is not completely configured yet. To build the Docker image, execute the following command in the project directory:
    
    ```bash
    docker build -t appium-tests .
    ```
    After the Docker image is built, you can run the Docker container using the following command:
    
    ```bash
    docker run appium-tests
    ```


## Project Progress
Note: 
I have only managed to complete the Video tab task as I encountered some challenges during the development process. This is the first time I have used Kotlin and Cucumber to develop Appium tests, which resulted in a learning curve.

Working with Kotlin and configuring the Gradle dependencies took some time to figure out. Additionally, the deprecation of mobileBy, mobileElement, and WebDriverWait in the V8 Java client added complexity to developing common utility functions, requiring extra effort and research. Implementing the scrollToTheEndOfTheScreen functionality also posed a challenge.

Furthermore, I faced difficulties launching the app in the emulator while using the APP_ACTIVITY capability in the desired capabilities. This issue consumed significant time during the implementation.

Moreover, addressing the AUTO_GRANT_PERMISSIONS capability to disable pop-ups on the emulator proved to be challenging. Previously, I had primarily used Appium with real devices, so automating tests using an emulator introduced cache clearance issues and further delays.

As a result, only the Video tab task has been fully completed at this stage. I plan to continue working on the remaining tasks and resolve the challenges related to the emulator to achieve the project's objectives.

Additionally, please note that the Docker implementation is not complete. Ideally, I would use either BrowserStack or LambdaTest with the app_url to run the tests using Docker. However, there are some configuration issues to address. The JAVA_HOME environment variable was not being correctly set inside the Docker container, which needs to be resolved.