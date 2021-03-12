#!/bin/bash
# /Users/biznw/Documents/GitHub/jarvis_data_eng_YerinAn/linux_sql/scripts

function setup() {
    hostname=$(hostname -f)
}

if [ "$1" == "setup" ]
  then echo "set up"
fi