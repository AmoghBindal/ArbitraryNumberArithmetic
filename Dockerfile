# Use a base image with Java and Ant
FROM eclipse-temurin:21-jdk

# Install ant
RUN apt-get update && \
    apt-get install -y ant && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy all project files into the container
COPY . /app

# Build the project using Ant
RUN ant jar

# Set the default command
# This allows running like: docker run myinfarith int add 123 456
ENTRYPOINT ["java", "-jar", "dist/MyInfArith.jar"]
