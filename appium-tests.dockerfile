# Use Ubuntu as the base image
FROM ubuntu:latest

# Set environment variables for Android SDK
ENV ANDROID_HOME=/opt/android-sdk-linux
ENV PATH=${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools

# Set JAVA_HOME environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

# Install required dependencies
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    unzip \
    wget \
    curl \
    openjdk-8-jdk \
    && apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Check internet connectivity
RUN curl -sSf http://google.com >/dev/null || (echo "Error: Failed to connect to the internet!" && exit 1)

# Set working directory
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the gradlew script and gradle directory
COPY gradlew .
COPY gradle/ gradle/

# Copy the source code
COPY src/ src/

ENTRYPOINT ["bash"]

#
# # Build the project
# RUN ./gradlew build
#
# # Set the entry point for running the tests
# ENTRYPOINT ["./gradlew", "cucumber"]
