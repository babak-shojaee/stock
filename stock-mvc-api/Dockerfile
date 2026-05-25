FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /opt
COPY target/*.jar /opt/stock.jar
ENTRYPOINT ["java","-jar","/opt/stock.jar"]
