# eMobility Dockerfile by MS
#
# VERSION 16.10
FROM debian:jessie
MAINTAINER msbeiti

ENV DEBIAN_FRONTEND noninteractive

# Include dist
ADD dist/ /root/dist/

# Setup apt
RUN apt-get update -y && \
    apt-get upgrade -y && \

# Install packages
    apt-get install -y net-tools cron supervisor python git python-twisted python-pycryptopp python-pyasn1 \
                       python-zope.interface tcpdump expect-dev wget mysql-server && \

# Installing and setting up mysql 
    mv /root/dist/scripts/initDB.sql /tmp/ && \
    service mysql start && /usr/bin/mysqladmin -u root password "admin" && /usr/bin/mysql -u root -p"admin" < /tmp/initDB.sql && \

# Installing Java 8
    wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" download.oracle.com/otn-pub/java/jdk/8u72-b15/jdk-8u72-linux-x64.tar.gz && \
    mkdir /opt/oracleJava/ && \
    tar xvf jdk-8u72-linux-x64.tar.gz -C /opt/oracleJava/ && \
    update-alternatives --install "/usr/bin/java" "java" "/opt/oracleJava/jdk1.8.0_72/bin/java" 1 && \
    update-alternatives --install "/usr/bin/javac" "javac" "/opt/oracleJava/jdk1.8.0_72/bin/javac" 1 && \
    update-alternatives --install "/usr/bin/javaws" "javaws" "/opt/oracleJava/jdk1.8.0_72/bin/javaws" 1 && \
    update-alternatives --install "/usr/bin/jar" "jar" "/opt/oracleJava/jdk1.8.0_72/bin/jar" 1 && \

# Download the eMobility honeynet from git
    mkdir /opt/emobility/ && git clone https://github.com/dtag-dev-sec/emobility.git /opt/emobility/ && \

## Installating of the central system from source code (neccesary to create a.o. all the tables in database)
# Installing maven
    wget www.eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz && \
    mkdir /opt/maven/ &&\
    tar xzvf apache-maven-3.3.9-bin.tar.gz -C /opt/maven/ && \

# Compiling the central system
    service mysql start && \
    cd /opt/emobility/src/centralsystem && \
    /opt/maven/apache-maven-3.3.9/bin/mvn package && \
    mv /opt/emobility/src/centralsystem/target/CentralSystem-2.0.1.jar /opt/emobility/src/centralsystem/target/CentralSystem.jar && \
##

# Register charge points and users at the central system
    service mysql start && \
    cd /opt/emobility/src/configurationmanager && \
    java -cp ./lib/com.mysql.jdbc_5.1.5.jar:./bin de.tudortmund.cni.ict4es.config.MainGenerator && \

# Create directories, setup user, groups and configs
    addgroup --gid 2000 tpot && \
    adduser --system --no-create-home --shell /bin/bash --uid 2000 --disabled-password --disabled-login --gid 2000 tpot && \
    mv /root/dist/supervisord.conf /etc/supervisor/conf.d/supervisord.conf && \
    mv /root/dist/crontab /etc/crontab && \

# Setup ewsposter
    apt-get install -y python-lxml python-mysqldb python-requests git && \
    git clone https://github.com/rep/hpfeeds.git /opt/hpfeeds && cd /opt/hpfeeds && python setup.py install && \
    git clone https://github.com/armedpot/ewsposter.git /opt/ewsposter && \
    mkdir -p /opt/ewsposter/spool /opt/ewsposter/log && \

# Clean up 
    rm -rf /root/* && \
    apt-get purge git -y && \
    apt-get autoremove -y && \
    apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Start supervisor
CMD ["/usr/bin/supervisord","-c","/etc/supervisor/supervisord.conf"]
