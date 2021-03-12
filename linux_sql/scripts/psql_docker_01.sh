#!/bin/bash

export db_password='password'
export db_username='yerin'

# CHECK WHETHER A CONTAINER CREATED OR NOT
function is_created_container(){
  check_container=$(docker container ls -a -f name=jrvs-psql | grep jrvs-psql)
  if [[ $check_container ]]
    then
	  result=1
  else
	  result=0
  fi
  return $result
}

# GET ERROR MESSAGE IF AN ERROR OCCUR, STOP SCRIPT
function error_mes_container() {
  is_created_container
  check=$?
  if [ $check == "$1" ]
  then
    echo "$2"
    exit 1
  fi
}

function error_mes_db(){
  if [ $db_password == "" ] || [ $db_username == "" ]
  then
    echo "ERROR: password or username is missing"
  fi
}

function create_container(){
  sudo systemctl status docker || systemctl start docker
  docker pull postgres
  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -e POSTGRES_USER=$db_username -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
}

function get_create() {
  is_created_container
  error_mes_container 1 "ERROR:Container exist"
  error_mes_db
  create_container
  echo "DOCKER CONTAINER CREATED"
}

function get_start() {
  error_mes_container 0 "ERROR:CONTAINER IS NOT EXIST"
  docker container start jrvs-psql
  echo "DOCKER CONTAINER IS RUNNING"
}

function get_stop() {
  error_mes_container 0 "ERROR:CONTAINER IS NOT EXIST"
  docker container stop jrvs-psql
  echo "DOCKER CONTAINER IS STOPPED"
}

if [ "$1" == "create" ]
  then get_create
fi

if [ "$1" == "start" ]
  then get_start
fi

if [ "$1" == "stop" ]
  then get_stop
fi

if [ "$1" == "check" ]
  then if [ "$2" == "" ]
    then docker container ls -a
    else docker container ls -a -f name="$2"
  fi
fi

function get_remove() {
  docker container rm jrvs-psql
  echo "DOCKER CONTAINER IS REMOVED"
}

if [ "$1" == "remove" ]
  then get_remove
fi

list=('create' 'start' 'stop' 'remove' 'check')

if [ "$1" != "$(compgen -W "${list[*]}" "$1" | head -1)" ]
  then echo "ERROR: INVALID ACTION"
  exit 1
fi