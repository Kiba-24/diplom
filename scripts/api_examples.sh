#!/usr/bin/env bash
set -e
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login -H 'Content-Type: application/json' -d '{"username":"operator","password":"operator123"}' | jq -r .token)
curl -s http://localhost:8080/api/tickets -H "Authorization: Bearer $TOKEN"
