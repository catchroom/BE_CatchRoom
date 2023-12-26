#!/usr/bin/env bash

REPOSITORY=/opt/catchroom
cd $REPOSITORY

APP_NAME=catchroom-deploy
JAR_NAME=$(ls $REPOSITORY/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME


CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

CURRENT_TIME=$(date+%Y%m%d_%H%M%S)
LOG_PATH=/home/ubuntu/log/catchroom.log
LOG_BACKUP="catchroom_$CURRENT_TIME.log"

cp $LOG_PATH $LOG_BACKUP
cat /dev/null > $LOG_PATH

echo "> $JAR_PATH 배포"
sudo -u ubuntu nohup java -jar $JAR_PATH > $LOG_PATH
