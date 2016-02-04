# eMobility Dockerfile by MS
#
# VERSION 1.0
FROM ubuntu:14.04.3
MAINTAINER msbeiti

# Setup apt
RUN apt-get update -y && \
    apt-get dist-upgrade -y
ENV DEBIAN_FRONTEND noninteractive

# Install packages
RUN apt-get install -y supervisor python git python-twisted python-pycryptopp python-pyasn1 python-zope.interface tcpdump expect-dev

# Installing and setting up mysql 
RUN apt-get -y install mysql-server-5.6
ADD initDB.sql /tmp/
RUN service mysql start && /usr/bin/mysqladmin -u root password "admin" && /usr/bin/mysql -u root -p"admin" < /tmp/initDB.sql

# Installing Java 8
RUN apt-get install -y wget && wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" download.oracle.com/otn-pub/java/jdk/8u72-b15/jdk-8u72-linux-x64.tar.gz
RUN mkdir /opt/oracleJava/ && \
    tar xvf jdk-8u72-linux-x64.tar.gz -C /opt/oracleJava/ && \
    update-alternatives --install "/usr/bin/java" "java" "/opt/oracleJava/jdk1.8.0_72/bin/java" 1 && \
    update-alternatives --install "/usr/bin/javac" "javac" "/opt/oracleJava/jdk1.8.0_72/bin/javac" 1 && \
    update-alternatives --install "/usr/bin/javaws" "javaws" "/opt/oracleJava/jdk1.8.0_72/bin/javaws" 1 && \
    update-alternatives --install "/usr/bin/jar" "jar" "/opt/oracleJava/jdk1.8.0_72/bin/jar" 1 

# Download the eMobility honeynet from git
RUN mkdir /opt/emobility/ && git clone https://github.com/dtag-dev-sec/emobility.git /opt/emobility/

## Installating of the central system from source code (neccesary to create a.o. all the tables in database)
# Installing maven
RUN wget www.eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
RUN mkdir /opt/maven/ &&\
tar xzvf apache-maven-3.3.9-bin.tar.gz -C /opt/maven/ 
# Compiling the central system
RUN service mysql start && cd /opt/emobility/src/centralsystem && /opt/maven/apache-maven-3.3.9/bin/mvn package &&  mv /opt/emobility/src/centralsystem/target/CentralSystem-2.0.1.jar /opt/emobility/src/centralsystem/target/CentralSystem.jar
##

# Register charge points and users at the central system
RUN service mysql start && cd /opt/emobility/src/configurationmanager && java -cp ./lib/com.mysql.jdbc_5.1.5.jar:./bin de.tudortmund.cni.ict4es.config.MainGenerator

# Create directories, setup user, groups and configs
RUN addgroup --gid 2000 tpot && \
    adduser --system --no-create-home --shell /bin/bash --uid 2000 --disabled-password --disabled-login --gid 2000 tpot 
ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Clean up 
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && rm /*.gz

# Start supervisor
CMD ["/usr/bin/supervisord"]
