#!/bin/bash
services=(
  "user 8080:80"
  "wallet 8081:80"
  "game 8082:80"
  "notification 8083:80"
  "promotion 8084:80"
  "history 8085:80"
)

for svc in "${services[@]}"; do
  set -- $svc
  kubectl port-forward svc/$1 $2 &
done