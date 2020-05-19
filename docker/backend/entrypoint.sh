#!/bin/bash
set -e

gradle build --continuous &

exec "$@"