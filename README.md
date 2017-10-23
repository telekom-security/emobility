[![](https://images.microbadger.com/badges/version/dtagdevsec/emobility:1710.svg)](https://microbadger.com/images/dtagdevsec/emobility:1710 "Get your own version badge on microbadger.com") [![](https://images.microbadger.com/badges/image/dtagdevsec/emobility:1710.svg)](https://microbadger.com/images/dtagdevsec/emobility:1710 "Get your own image badge on microbadger.com")

# eMobility

[eMobility](https://github.com/dtag-dev-sec/emobility) is a high-interaction honeynet with the goal to collect intelligence about the motives and methods of adversaries targeting next-generation transport infrastructure. The eMobility honeynet consists of a charging central system, serveral charge points, and simulated user transactions. Once the attacker gets access to the web interface of the central system, the attacker will be able to reconfigure the system, monitor and manipulate running charging transactions, and interact with the charge points. On top of that, at random times, users start charging their vehicles  and the attacker might interact with those users.

This repository contains the necessary files to create a *dockerized* version of eMobility.

The `Dockerfile` contains the blueprint for the dockerized eMobility and will be used to setup the docker image.  

The `docker-compose.yml` contains the necessary settings to test eMobility using `docker-compose`. This will ensure to start the docker container with the appropriate permissions and port mappings.

# eMobility Dashboard

![eMobility Dashboard](https://raw.githubusercontent.com/dtag-dev-sec/emobility/master/doc/dashboard.png)
