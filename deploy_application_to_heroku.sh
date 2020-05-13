#!/bin/bash

#Expectet arguments:
# $1 - maven profile

# Check if profile was passed as an argument
if [[ $# -eq 0 ]]
then
  echo 'Name of maven profile is expected as $1 parameter'
  exit
fi

# Utils
function prop {
    grep "${1}" src/main/resources/env/application.prod.properties|cut -d'=' -f2
}

# Jar
echo "Creating jar for $1 profile"
mvn package -P"$1"

ARTIFACT_ID=$(echo -e 'setns x=http://maven.apache.org/POM/4.0.0\ncat /x:project/x:artifactId/text()' | xmllint --shell pom.xml | grep -v /)
echo "ARTIFACT_ID: $ARTIFACT_ID"
VERSION=$(echo -e 'setns x=http://maven.apache.org/POM/4.0.0\ncat /x:project/x:version/text()' | xmllint --shell pom.xml | grep -v /)
echo "VERSION: $VERSION"

# Docker image
HEROKU_USER=$(prop 'heroku.user')
HEROKU_API_KEY=$(prop 'heroku.api.key')
HEROKU_APP_NAME=$(prop 'heroku.app.name')

echo "Logging into container"
heroku container:login
echo "Logging into registry.heroku.com with user: $HEROKU_USER"
docker login --username="$HEROKU_USER" --password="$HEROKU_API_KEY" registry.heroku.com

echo "Creating docker image from $ARTIFACT_ID-$VERSION.jar"
docker build --build-arg JAR_FILE=target/"$ARTIFACT_ID-$VERSION".jar -t registry.heroku.com/"$HEROKU_APP_NAME"/web .

HEROKU_APP_NAME=$(prop 'heroku.app.name')
docker push registry.heroku.com/"$HEROKU_APP_NAME"/web

# Release in heroku
./release-app-in-heroku.sh "$HEROKU_APP_NAME" "$HEROKU_API_KEY"