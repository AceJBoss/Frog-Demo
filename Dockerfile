FROM openjdk:8
EXPOSE 5000
ADD target/frog-demo.jar frog-demo.jar
ENTRYPOINT ["java","-jar","/frog-demo.jar"]
