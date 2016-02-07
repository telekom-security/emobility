#!/bin/bash
ifconfig eth0:1 10.0.1.11
ifconfig eth0:2 10.0.1.12
ifconfig eth0:3 10.0.1.13
ifconfig eth0:4 10.0.1.14
ifconfig eth0:5 10.0.1.15
ifconfig eth0:6 10.0.1.16
ifconfig eth0:7 10.0.1.17
ifconfig eth0:8 10.0.1.18
ifconfig eth0:9 10.0.1.19
ifconfig eth0:10 10.0.1.20
ifconfig eth0:11 10.0.1.21
sleep 5
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_2.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_3.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_4.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_5.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_6.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_7.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_8.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_9.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_10.xml -b true -s true &
java -jar /opt/emobility/src/chargepoint/target/ChargePoint.jar -c /opt/emobility/conf/chargepoint_11.xml -b true -s true & 

