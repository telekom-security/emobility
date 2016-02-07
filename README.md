# Dockerized eMobility in T-Pot

eMobility is a high-interaction honeynet with the goal to collect intelligence about the motives and methods of adversaries targeting next-generation transport infrastructure. The eMobility honeynet consists of a charging central system, serveral charge points, and simulated user transactions. Once the attacker gets access to the web interface of the central system, the attacker will be able to reconfigure the system, monitor and manipulate running charging transactions, and interact with the charge points. On top of that, at random times, users start charging their vehicles  and the attacker might interact with those users.

This repository contains the necessary files to create a *dockerized* version of eMobility. 

The `Dockerfile` contains the blueprint for the dockerized eMobility and will be used to setup the docker image.  

The `conf` directory comprises all necessary files to configure the charge points and the simulated user transactions. 

The `supervisord.conf` is used to start eMobility under supervision of supervisord. 

In case you want to run the dockerized eMobility independently, you must modify the config files to match your environment and rebuild the docker image.

Using upstart, copy the `upstart/eMobility.conf` to `/etc/init/eMobility.conf` and start using

`service emobility start`

This will make sure that the docker container is started with the appropriate rights. Further, it autostarts during boot. In the T-Pot setup, some ports are excluded as they need to be reserved for other honeypot daemons running in parallel.

