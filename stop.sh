#!/usr/bin/env bash
PORT=8080
curl http://localhost:${PORT}/admin?shutdown=1000
