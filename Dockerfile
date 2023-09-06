FROM maven:3.9.0-eclipse-temurin-19 AS stage1
COPY ./src /app/src
COPY ./pom.xml /app
WORKDIR /app
RUN mvn clean package

FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk19
ENV WILDFLY_HOME $JBOSS_HOME
ENV WILDFLY_CLI $WILDFLY_HOME/bin/jboss-cli.sh
ENV DB_USER developer
ENV DB_PASS password
ENV DB_URI host.docker.internal:3306
ENV DB_NAME footballers_db
RUN bash -c '$WILDFLY_HOME/bin/standalone.sh &' && \
      bash -c 'until `$WILDFLY_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$WILDFLY_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
      curl --location --output /tmp/mysql-connector-j-8.0.32.jar --url https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.32/mysql-connector-j-8.0.32.jar && \
      $WILDFLY_CLI --connect --command="module add --name=com.mysql --resources=/tmp/mysql-connector-j-8.0.32.jar --dependencies=javax.api,javax.transaction.api" && \
      $WILDFLY_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.cj.jdbc.Driver)" && \
      $WILDFLY_CLI --connect --command="data-source add \
            --name=MySqlDS \
            --jndi-name=java:/MySqlDS \
            --user-name=${DB_USER} \
            --password=${DB_PASS} \
            --driver-name=mysql \
            --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME} \
            --use-ccm=false \
            --max-pool-size=25 \
            --blocking-timeout-wait-millis=5000 \
            --enabled=true" && \
    $WILDFLY_CLI --connect --command=":shutdown" && \
      rm -rf $WILDFLY_HOME/standalone/configuration/standalone_xml_history/ $WILDFLY_HOME/standalone/log/* && \
      rm -f /tmp/mysql-connector-j-8.0.32.jar
COPY --from=stage1 /app/target/ROOT.war $WILDFLY_HOME/standalone/deployments/
EXPOSE 8080