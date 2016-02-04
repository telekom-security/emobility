#!/bin/bash
/opt/emobility/scripts/startCentralSystem.sh 
sleep 60
/opt/emobility/scripts/startChargePoints.sh 
sleep 5
/opt/emobility/scripts/startLog.sh 
