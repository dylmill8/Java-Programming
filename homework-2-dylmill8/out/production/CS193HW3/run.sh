#!/bin/bash

javac -d out -cp out:junit-platform-console-standalone-1.7.0-M1.jar TestCases.java Questions.java
java -jar junit-platform-console-standalone-1.7.0-M1.jar --class-path out --scan-class-path > temp.txt

while IFS= read -r line
do
  if [[ $line == *"JUnit Vintage:TestCases"* ]]; then
    echo -e "\e[1;34m$line\e[0m"
  elif [[ $line == *"tests failed"* ]]; then
    echo -e "\e[0;31m$line\e[0m"
  elif [[ $line == *"tests successful"* ]]; then
    echo -e "\e[92m$line\e[0m"
  elif [[ $line == *"Failures"* ]]; then
    echo -e "\e[0;31m$line\e[0m"
  else
    echo $line
  fi
done < temp.txt

rm temp.txt
