#!/bin/bash

#Expected arguments
# $1 = Heroku application name
# $2 = Heroku API key

if [[ $# -ne 2 ]]
then
  echo "Application name is expected as $1 parameter and API key as $2 parameter"
  exit
fi

IMAGE_ID=$(docker inspect registry.heroku.com/"$1"/web --format={{.Id}})
echo "Image id: $IMAGE_ID"
PAYLOAD='{"updates":[{"type":"web","docker_image":"'"$IMAGE_ID"'"}]}'
echo "Payload: $PAYLOAD"

echo "Releasing Heroku application with name $1."
curl \
 --netrc -X PATCH https://api.heroku.com/apps/timeforpizza-backend/formation \
 -d "$PAYLOAD" \
 -H "Content-Type: application/json" \
 -H "Accept: application/vnd.heroku+json; version=3.docker-releases" \
 -H "Authorization: Bearer $2"