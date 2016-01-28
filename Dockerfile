FROM msteve/emobility
MAINTAINER msbeiti


# Create directories, setup user, groups and configs
RUN addgroup --gid 2000 tpot
RUN adduser --system --no-create-home --shell /bin/bash --uid 2000 --disabled-password --disabled-login --gid 2000 tpot
RUN mkdir -p /data/eMobility/ /data/eMobility/conf /data/eMobility/log
COPY configs/* /data/eMobility/conf/
RUN chmod 760 -R /data/ && chown tpot:tpot -R /data/
ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf


CMD ["/usr/bin/supervisord"]


