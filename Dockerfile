FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache bash procps curl tar

ENV MAVEN_HOME /usr/share/maven

COPY --from=maven:3.9.7-eclipse-temurin-11 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.7-eclipse-temurin-11 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.7-eclipse-temurin-11 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

ARG MAVEN_VERSION=3.9.7
ARG USER_HOME_DIR="/root"
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV DEFAULT_VARIABLE="client"


WORKDIR /src/main/java
COPY . .

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "exec:java", "-Dexec.args="]
CMD ["-Dexec.args=${DEFAULT_VARIABLE}"]