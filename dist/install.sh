#!/bin/sh

# Install packages
apk -U add bash coreutils dcron expect maven mariadb mariadb-client net-tools openjdk8 procps supervisor tcpdump

# Move source
mkdir -p /opt/emobility /etc/supervisor.d /etc/periodic/midnight /data/eMobility
cp -R /root/dist/conf /opt/emobility/
cp -R /root/dist/scripts /opt/emobility/
cp -R /root/dist/src /opt/emobility/

# Setup cron
mv /root/dist/crontab /etc/periodic/midnight/crontab
chmod +x /etc/periodic/midnight/crontab
crontab -l | { cat; echo "0       0       *       *       *       run-parts /etc/periodic/midnight"; } | crontab -

# Installing and setting up mysql 
/usr/bin/mysql_install_db
/usr/bin/mysqld_safe --user=root &
sleep 10
/usr/bin/mysqladmin -u root password "admin" && /usr/bin/mysql -u root -p"admin" < /root/dist/scripts/initDB.sql

# Compiling the central system
cd /opt/emobility/src/centralsystem
/usr/bin/mvn package
mv /opt/emobility/src/centralsystem/target/CentralSystem-2.0.1.jar /opt/emobility/src/centralsystem/target/CentralSystem.jar

# Register charge points and users at the central system
cd /opt/emobility/src/configurationmanager
java -cp ./lib/com.mysql.jdbc_5.1.5.jar:./bin de.tudortmund.cni.ict4es.config.MainGenerator

# Create directories, groups and configs
mv /root/dist/supervisord.conf /etc/supervisor.d/emobility.ini

# Clean up
apk del maven --purge
rm -rf /root/*
