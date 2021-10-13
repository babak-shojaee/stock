FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
WORKDIR /opt
COPY target/*.jar /opt/stock.jar
ENTRYPOINT ["java","-jar","/opt/stock.jar"]