#!/bin/bash

# Automated deployment script

WAR='SpringBlog-production-0.1.war'
IP='114.215.81.109'
REMOTE_DIR='/root/'

echo "Set app.properties to production mode..."

DIR='src/main/resources'

mv $DIR/app.properties $DIR/app.properties.backup
cp production_app.properties $DIR/app.properties


echo "Build distribution war $WAR ..."
./gradlew warProduction

echo 'Restore app.properties...'
rm $DIR/app.properties
mv $DIR/app.properties.backup $DIR/app.properties

echo "Upload $WAR to server $IP dir $REMOTE_DIR"
scp build/dist/$WAR root@$IP:$REMOTE_DIR

echo "Finished uploading."
echo "Restart remote server application.."

python deploy.py
