#!/usr/bin/env bash

set -e

if [ -z "$1" ]; then
  echo "Usage: ./update-versions.sh <new_version>"
  exit 1
fi

NEW_VERSION="$1"

if ! [[ "$NEW_VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+(-[A-Za-z0-9.-]+)?$ ]]; then
  echo "Invalid version format. Expected: x.y.z or x.y.z-suffix"
  exit 1
fi

echo "Updating versions to $NEW_VERSION..."

find . -name "README.md" -type f -exec \
  sed -E -i "s/[0-9]+\.[0-9]+\.[0-9]+(-[A-Za-z0-9.-]+)?/${NEW_VERSION}/g" {} \;

echo "Done."