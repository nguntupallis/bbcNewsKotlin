# BBC News Kotlin Appium tests
This is a sample test framework to run Kotlin appium tests on Android device

## Prerequisites

Before running the project, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 11
- Gradle build tool
- Android SDK
- Docker

## Setup Instructions

Follow these steps to set up and run the project:

1. Clone the repository:

   ```bash
   git clone <repository-url>
2. Install the JDK 11 if you haven't already. You can download it from the official website or use your preferred package manager.

3. Install Gradle. You can download it from the official website or use your preferred package manager.

4. Install the Android SDK. You can follow the instructions provided by Google in the official documentation.

5. Install Docker. You can download it from the official website based on your operating system.
6. Build the Docker image:

```bash
docker build -t appium-tests .
```
7. Run the Docker container:
```bash
docker run appium-tests
```
## Project Structure

The project follows a standard directory structure for organizing Kotlin Appium tests. Here's an overview of the important directories and their purpose:

- **src/main/kotlin**: This directory contains the main Kotlin source code of the project. It typically includes the implementation of utility classes, page objects, and other application-specific code.

- **src/test/kotlin**: This directory contains the test source code of the project. It includes the implementation of test cases, step definitions, and other test-related code.

- **src/test/resources**: This directory is used for storing test resources such as feature files and test data. Feature files define the behavior of the tests using Gherkin syntax.

- **build.gradle.kts**: This file is the Gradle build script written in Kotlin DSL. It defines project dependencies, build configurations, and other build-related settings.

- **settings.gradle.kts**: This file is also a Gradle build script written in Kotlin DSL. It specifies the modules or sub-projects included in the build.

- **Dockerfile**: This file is used to build a Docker image for running the Appium tests in a containerized environment. It includes the necessary setup and dependencies for executing the tests.

- **README.md**: This file contains information about the project, setup instructions, and other relevant details. It serves as a documentation guide for users and developers.


