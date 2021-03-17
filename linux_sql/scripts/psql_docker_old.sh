##!/bin/bash
## script usage
## ./scripts/psql_docker_old.sh start|stop|create [db_username][db_password]
#export db_password='password'
#export db_username='yerin'
#
## CHECK WHETHER A CONTAINER CREATED OR NOT
#function is_created_container(){
#  check_container=$(docker container ls -a -f name=jrvs-psql | grep jrvs-psql)
#  if [[ $check_container ]]
#    then
#	  result=1
#  else
#	  result=0
#  fi
#  return $result
#}
#
## GET ERROR MESSAGE IF AN ERROR OCCUR, STOP SCRIPT
#function error_mes_container() {
#  is_created_container
#  check=$?
#  if [ $check == $1 ]
#  then
#    echo "$2"
#    exit 1
#  fi
#}
#
## SEND ERROR MESSAGE REGARDING SETTING USERNAME AND PASSWORD
#function error_mes_db(){
#  if [ $db_password == "" ] || [ $db_username == "" ]
#  then
#    echo "ERROR: password or username is missing"
#  fi
#}
#
#echo -e "------ 1. Check container  ------"
#error_mes_container 1 "ERROR:Container exist"
#
#echo -e "\n\n------  2. Create container -------"
#sudo systemctl status docker || systemctl start docker
#docker pull postgres
#docker volume create pgdata
#docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -e POSTGRES_USER=$db_username -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
#
#echo -e "\n\n------  3. Check db and container -------"
#error_mes_db
#error_mes_container 0 "ERROR:CONTAINER IS NOT CREATED"
#docker container start jrvs-psql
#docker container stop jrvs-psql
## docker container rm jrvs-psql
#
#
#
##!/bin/bash
## /Users/biznw/Documents/GitHub/jarvis_data_eng_YerinAn/linux_sql/scripts
#
#hostname=$(hostname -f)
#lscpu_out=`lscpu`
#cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
#cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
#cpu_model=$(echo "$lscpu_out"  | egrep "^Model:" | awk '{print $2}' | xargs)
#cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU\sMHz" | awk '{print $3}' | xargs)
#l2_cache=$(echo "$lscpu_out"  | egrep "^L2\scache:" | awk '{print $3}' | xargs)
#memory_info=$(cat /proc/meminfo)
#total_mem=$(echo "$memory_info"  | egrep "^MemTotal:" | awk '{print $2"\t"$3}' | xargs)
#timestamp=$(echo "$(date -u)" | awk '{print $1"\t"$2"\t"$3"\t"$4"\t"$5"\t"$6}' | xargs)
#
#function print_() {
#    echo -e "$1: $2\n"
#}
#
##list=( 'hostname' 'cpu_number' 'cpu_architecture' 'cpu_model' 'cpu_mhz' 'l2_cache' 'memory_info' 'total_mem' 'timestamp' )
##for i in "${list[@]}"
##do
##  print_ "$i"
##done
#
#print_ hostname "$hostname"
#print_ cpu_number "$cpu_number"
#print_ totalmemory "$total_mem"
#print_ timestamp "$timestamp"
#
## execute - bash host_info.sh postgres 5432:5432
#
