FROM alpine:edge
MAINTAINER juanlumn@gmail.com
RUN apk add --no-cache openjdk8
COPY /build/libs/questions-crud-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/questions-crud-0.0.1-SNAPSHOT.jar"]
VOLUME /var/lib/spring-cloud/questions-crud