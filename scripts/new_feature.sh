#!/usr/bin/env bash

# Check that we are on develop branch
CURRENT_BRANCH=`git rev-parse --abbrev-ref HEAD`
if [ $CURRENT_BRANCH != "develop" ]; then
  echo -e "\nYou must be on develop branch to create a new feature.\n"
  exit 1
fi

# Check that local and remote branches are synchronized
git fetch origin develop
c1=`git rev-parse HEAD`
c2=`git rev-parse FETCH_HEAD`
if [ $c1 != $c2 ]; then
  echo -e "\nYour local and remote branches are not synchronized.\n"
  exit 1
fi

# Create local feature branch and push it to the remote
FEATURE_BRANCH="feature/$1"
git checkout -b $FEATURE_BRANCH
git push -u origin $FEATURE_BRANCH

# Open a pull request from your feature branch into develop
curl -u "NicolasRichel" \
     -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: application/vnd.github.v3+json" \
     -d '{ "title": "'$1'", "head": "'$FEATURE_BRANCH'", "base": "develop" }' \
     https://api.github.com/repos/NicolasRichel/jarialtekin-api-java/pulls

exit 0
