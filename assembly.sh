#!/usr/bin/env bash
rm *.jar
mvn compile assembly:single
mv ./target/java-game-server-1.0-jar-with-dependencies.jar ./