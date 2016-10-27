# Introduction 

The charge point project implements the Open Charge Point Protocol (OCPP) version 1.5 to have a bidirectional communication with the central system. To this end, web services adopting the SOAP 1.2 message framework are implemented.  Examples of supported service invocations are:

Charge point: data transfer, reserve now, remote start/stop transaction, change configuration, etc.

Central system: authorize, boot notification, heartbeat, status notification, etc.
 
Each charge point has a corresponding xml.file that describes its personality.  Apart from that, this project simulates the charging behavior of users, which can be adjusted in the "charge_simulation" file. 
 
The charge point project was developed at the Communication Networks Institute (CNI - Prof. Dr.-Ing. Christian Wietfeld) at Technische Universitaet Dortmund, Germany  in cooperation with the Deutsche Telekom. It is distributed under [GPL](LICENSE.txt) and is free to use.

# Requirements 

1. You need JRE 8.

2. You need to create a virtual interface that has the IP address of the charge point, defined in the corresponding xml file. From the attacker's point of view, each charge point has its own IP address and all the charge points listen on the same port, as in practice. In fact, all the charge points share the same interface and are listening on different ports. Run: `ifconfig eth0:0 <IP>`


# How to use?
Run: `java -jar <path/chargingpoint.jar> -c <path/chargepoint-config.xml> -b <true: if the charge point should automatically register itself at the central system> -s <true: if this charge point should be used to simulate active charging processes>`
