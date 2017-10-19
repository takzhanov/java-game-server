#!/usr/bin/env bash
export PATH=${JAVA_HOME}/bin:$PATH

#java -Dlog4j.configurationFile=cfg/log4j2.xml -jar server.jar 8080

PORT=8080
java -jar target/server.jar ${PORT}

#система тестирования запускала так: java -jar target/server.jar
