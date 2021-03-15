#!/bin/bash
command=$1
export db_password=$2  #'password'
export db_username=$3  #'yerin'
container_name=$4

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
  if [ "$2" == "" ] || [ "$3" == "" ]
  then
    echo "ERROR: password or username is missing"
  fi
}

function create_container(){
  sudo systemctl status docker || systemctl start docker
  docker pull postgres
  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_PASSWORD="$2" -e POSTGRES_USER="$3" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
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

function check_status() {
  if [ "$1" == "check" ]
  then if [ "$2" == "" ]
    then docker container ls -a
    else docker container ls -a -f name="$4"
    fi
  fi
}

function get_remove() {
  docker container rm jrvs-psql
  echo "DOCKER CONTAINER IS REMOVED"
}

list=('create' 'start' 'stop' 'remove' 'check')

case $1 in
  "${list[0]}")
    get_create
    exit $?
    ;;
  "${list[1]}")
    get_start
    exit $?
    ;;
  "${list[2]}")
    get_stop
    exit $?
    ;;
  "${list[4]}")
    check_status
    exit $?
    ;;
  "${list[3]}")
    get_remove
    exit $?
    ;;
  *)
  ;;
esac



if [ "$1" != "$(compgen -W "${list[*]}" "$1" | head -1)" ]
  then echo "ERROR: INVALID ACTION"
  exit 1
fi