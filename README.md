# Frog Demo

**How to run**


Clone the Frog Demo project then
open intelliJ idea and clone using VCS, Paste the link gotten from the cloning
Ensure to install _java_ JDK 1.8
Install _maven_
the dependencies will be downloaded automatically, and the project should be ready to
run but if that's not the case then kindly run the `mvn install` command in the directory of the project where you can
find the _pom.xml_ file.

**Testing**

The application can be tested by running the _mvn test_ command in the project root directory.


**Containerization**

I added a docker file for app containerization please run a maven install in the root directory _"mvn install"_
Next run a docker build : "docker build -t frog-demo.jar ."
And Finally : "docker run -p 5000:5000 frog-demo.jar"