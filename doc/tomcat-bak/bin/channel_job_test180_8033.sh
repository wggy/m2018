#!/bin/sh

export CATALINA_OUT=/data/logs/tomcat/channel_job_test180.out
export LOGGING_CONFIG="-Djava.util.logging.config.file=../conf/logging.properties"
export SERVER_CONF="-config conf/channel_job_test180_8033.xml"
export JAVA_HOME=/usr/java/jdk1.8.0_111/
export JRE_HOME=/usr/java/jdk1.8.0_111/jre

#JAVA_OPTS='-server -Xrs -Xmx1536m -Xms1536m -Xmn512m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:SurvivorRatio=8 -Xverify:none -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8'
JAVA_OPTS='-server -Xrs -Xmx4G -Xms4G -Xss256k -XX:NewSize=1G -XX:MaxNewSize=1G -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:SurvivorRatio=8 -Xverify:none -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=10.206.2.180 -Dcom.sun.management.jmxremote.port=10222 -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCMSCompactAtFullCollection -XX:+UseCMSInitiatingOccupancyOnly -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xloggc:/data/program/tomcat/bin/gc.log'

. /data/program/tomcat/bin/execute.sh
