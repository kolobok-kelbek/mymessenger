#!/bin/bash
set -e

gradle build --continuous &
gradle flywayMigrate &

exec "$@"
