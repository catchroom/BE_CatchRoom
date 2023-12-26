#!/usr/bin/env bash

REPOSITORY=/opt/catchroom
cd $REPOSITORY

APP_NAME=catchroom-deploy
JAR_NAME=$(ls $REPOSITORY/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME
LOG_PATH=/home/ubuntu/log/catchroom.log


CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  # process kill
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
  # log backup
  CURRENT_TIME=$(date+%Y%m%d_%H%M%S)
  LOG_BACKUP="catchroom_$CURRENT_TIME.log"
  sudo -u ubuntu cp $LOG_PATH $LOG_BACKUP
  sudo -u ubuntu cat /dev/null > $LOG_PATH
fi


echo "> $JAR_PATH 배포 >> sudo -u ubuntu nohup sh -c "nohup java -jar $JAR_NAME > $LOG_PATH 2>&1 &" &
sudo -u ubuntu nohup sh -c "nohup java -jar $JAR_NAME > $LOG_PATH 2>&1 &" &
