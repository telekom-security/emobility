#!/bin/bash

stdbuf -i0 -o0 -e0 tcpdump -i eth0 -n -A -s 1024 'tcp dst port 8080 and (tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354)'| unbuffer -p grep "IP\|POST" | stdbuf -i0 -o0 -e0 sed -r -e '{N; s/.* IP (.*?) > (.*?): .*POST (.*?) .*$/\1|\2|\3/}' -e "s/$/|`date +%Y-%m-%dT%H:%M:%S`/" > /data/eMobility/log/centralsystemEWS.log

