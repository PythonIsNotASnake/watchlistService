# watchlistService
API to memorized about watched series and movies

# Deployment

## Deploy on ARM32 Architecture (e.g. Raspberry PI)
Build the jar-file with "./gradlew build" (if you need permissions for this go with "chmod +x gradlew" command to make gradlew executable)  
If you are on low performance use instead "./gradlew build -x test" to ignore tests during build.  
Build the docker image with "docker build --build-arg JAR_FILE=build/libs/watchlistService-0.0.1.jar -t watchlistservice:latest ."  
Run a docker container based on the image by "docker run --name watchlist -d -p 8080:8080 watchlistservice:latest"  
Add flag "--restart always" or "--restart unless-stopped" to docker run-commands if the container should start on startup of your machine.  
If docker isn't installed use "curl -sSL https://get.docker.com | sh" to install. To set user pi as docker admin use "sudo usermod -aG docker pi". To test if docker is installed correctly use "docker --version".  

## Deploy on ARM64 Architecture (e.g. Raspberry PI since Raspberry 3)
Go to the Dockerfile and change "FROM arm32v7/adoptopenjdk:latest" to "FROM arm64v8/adoptopenjdk:latest".  
Build the jar-file with "./gradlew build" (if you need permissions for this go with "chmod +x gradlew" command to make gradlew executable)  
If you are on low performance use instead "./gradlew build -x test" to ignore tests during build.  
Build the docker image with "docker build --build-arg JAR_FILE=build/libs/watchlistService-0.0.1.jar -t watchlistservice:latest ."  
Run a docker container based on the image by "docker run --name watchlist -d -p 8080:8080 watchlistservice:latest"  
Add flag "--restart always" or "--restart unless-stopped" to docker run-commands if the container should start on startup of your machine.  
If docker isn't installed use "curl -sSL https://get.docker.com | sh" to install. To set user pi as docker admin use "sudo usermod -aG docker pi". To test if docker is installed correctly use "docker --version".  
