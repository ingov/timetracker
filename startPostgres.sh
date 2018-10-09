#!/bin/bash

docker run -d --rm --publish 5432:5432 --name postgres mypostgres
