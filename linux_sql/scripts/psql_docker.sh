#!/bin/bash
# script usage
# ./scripts/psql_docker.sh start|stop|create [db_username][db_password]
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
  if [ $check == $1 ]
  then
    echo "$2"
    exit 1
  fi
}

# SEND ERROR MESSAGE REGARDING SETTING USERNAME AND PASSWORD
function error_mes_db(){
  if [ $db_password == "" ] || [ $db_username == "" ]
  then
    echo "ERROR: password or username is missing"
  fi
}

echo -e "------ 1. Check container  ------"
error_mes_container 1 "ERROR:Container exist"

echo -e "\n\n------  2. Create container -------"
sudo systemctl status docker || systemctl start docker
docker pull postgres
docker volume create pgdata
docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -e POSTGRES_USER=$db_username -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres

echo -e "\n\n------  3. Check db and container -------"
error_mes_db
error_mes_container 0 "ERROR:CONTAINER IS NOT CREATED"
docker container start jrvs-psql
docker container stop jrvs-psql
# docker container rm jrvs-psql

