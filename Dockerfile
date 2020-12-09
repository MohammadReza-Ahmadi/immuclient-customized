FROM openjdk:11
ADD target/docker-hello-spring-boot.jar docker-hello-spring-boot.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","docker-hello-spring-boot.jar"]


## commands ##############
## 1- build docker file :
#sudo docker build -f Dockerfile -t docker-spring-app .

## 2- run docker file :
#sudo docker run -p 8085:8085 docker-spring-app

## 3- list docker files :
#sudo docker images
