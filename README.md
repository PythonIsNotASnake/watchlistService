# watchlistService
API to memorized about watched series and movies

## Disclaimer: Watchlist is only for use inside a private network. The software does not support any kind of user management. So if you add data or logged in with third party accounts everyone has access to your created data.

1. [API Documentation](#api-documentation)
2. [Deployment](#deployment)  
2.1. [Configure application.properties](#configure-applicationproperties)  
2.2. [Deploy on ARM32 Architecture (e.g. Raspberry PI)](#deploy-on-arm32-architecture-eg-raspberry-pi)  
2.3. [Deploy on ARM64 Architecture (e.g. Raspberry PI since Raspberry 3)](#deploy-on-arm64-architecture-eg-raspberry-pi-since-raspberry-3)  
2.4. [Deploy on x86_64 Architecture (regular desktop pcs with Intel or AMD CPU)](#deploy-on-x86_64-architecture-regular-pcs-with-intel-or-amd-cpu)  

# API Documentation
After running the service the default path to get the swagger open api documentation is http://localhost:8080/swagger-ui  

# Deployment

## Configure application.properties 
Fill the empty properties in application.properties file to get all functions of watchlist.  
Create a dropbox app in the dropbox application center. It needs permissions to create, write and delete files in a separated directory.  
dropbox.appkey is the unique key of your dropbox app.  
dropbox.appsecret is the secret of your dropbox app.  
Create a mastodon app in the developer section of your mastodon settings.  
mastodon.appkey is the unique key of your dropbox app.  
mastodon.appsecret is the secret of your dropbox app.  

## Deploy on ARM32 Architecture (e.g. Raspberry PI)
Build the jar-file with "./gradlew build" (if you need permissions for this go with "chmod +x gradlew" command to make gradlew executable)  
If you are on low performance use instead "./gradlew build -x test" to ignore tests during build.  
Build the docker image with "docker build --build-arg JAR_FILE=build/libs/watchlistService-1.0.0.jar -t watchlistservice:latest ."  
Run a docker container based on the image by "docker run --name watchlist -d -p 8080:8080 watchlistservice:latest"  
Add flag "--restart always" or "--restart unless-stopped" to docker run-commands if the container should start on startup of your machine.  
If docker isn't installed use "curl -sSL https://get.docker.com | sh" to install. To set user pi as docker admin use "sudo usermod -aG docker pi". To test if docker is installed correctly use "docker --version".  

## Deploy on ARM64 Architecture (e.g. Raspberry PI since Raspberry 3)
Go to the Dockerfile and change "FROM arm32v7/adoptopenjdk:latest" to "FROM arm64v8/adoptopenjdk:latest".  
Build the jar-file with "./gradlew build" (if you need permissions for this go with "chmod +x gradlew" command to make gradlew executable)  
If you are on low performance use instead "./gradlew build -x test" to ignore tests during build.  
Build the docker image with "docker build --build-arg JAR_FILE=build/libs/watchlistService-1.0.0.jar -t watchlistservice:latest ."  
Run a docker container based on the image by "docker run --name watchlist -d -p 8080:8080 watchlistservice:latest"  
Add flag "--restart always" or "--restart unless-stopped" to docker run-commands if the container should start on startup of your machine.  
If docker isn't installed use "curl -sSL https://get.docker.com | sh" to install. To set user pi as docker admin use "sudo usermod -aG docker pi". To test if docker is installed correctly use "docker --version".  

## Deploy on x86_64 Architecture (regular PCs with Intel or AMD CPU)
Go to the Dockerfile and change "FROM arm32v7/adoptopenjdk:latest" to "FROM eclipse-temurin:latest".  
Build the jar-file with "./gradlew build" (if you need permissions for this go with "chmod +x gradlew" command to make gradlew executable)  
If you are on low performance use instead "./gradlew build -x test" to ignore tests during build.  
Build the docker image with "docker build --build-arg JAR_FILE=build/libs/watchlistService-1.0.0.jar -t watchlistservice:latest ."  
Run a docker container based on the image by "docker run --name watchlist -d -p 8080:8080 watchlistservice:latest"  
Add flag "--restart always" or "--restart unless-stopped" to docker run-commands if the container should start on startup of your machine.  
