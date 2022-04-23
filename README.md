# watchlistService
API to memorized about watched series and movies

# Deployment

## Deploy on ARM Architecture (e.g. Raspberry PI)
Build the MongoDB docker container with "docker run --name mongo -d -p 27017:27017 -p 28017:28017 andresvidal/rpi3-mongodb3:latest"  
Modify the application.properties by the Host-IP of your MongoDB instance to connect to the database.  
Build the jar-file with "./gradlew build" (if you need permissions for this go with "chmod +x gradlew" command to make gradlew executable)  
Build the docker image with "docker build --build-arg JAR_FILE=build/libs/watchlistService-0.0.1.jar -t latest ."  
Run a docker container based on the image by "docker run --name watchlist -d -p 8080:8080 latest:latest"  
Add flag "--restart always" to docker run-commands if the container should start on startup of your machine.  
