 FROM openjdk:21
 COPY target/shop-0.0.1-SNAPSHOT.jar app.jar
 COPY monitoring.yml ./monitoring.yml
 ENTRYPOINT ["java", "-jar", "app.jar", "--config.file=/monitoring/monitoring.yml"]