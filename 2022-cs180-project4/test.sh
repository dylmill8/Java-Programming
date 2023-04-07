#!/bin/sh

./setup.sh >/dev/null

for file in basicTests/*.exp; do
    diff -s "$file" "$(basename "$file" ".exp")"
done
