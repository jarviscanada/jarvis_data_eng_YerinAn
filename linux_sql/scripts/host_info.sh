#! /bin/bash

#Setup arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#validate arguments
if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

#parse hardware specification
hostname=$(hostname -f)
lscpu_out=`lscpu`
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model:" | awk '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU\sMHz" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2\scache:" | awk '{print $3}' | xargs)
memory_info=$(cat /proc/meminfo)
total_mem=$(echo "$memory_info"  | egrep "^MemTotal:" | awk '{print $2"\t"$3}' | xargs)
timestamp=$(echo "$(date -u)" | awk '{print $1"\t"$2"\t"$3"\t"$4"\t"$5"\t"$6}' | xargs)

insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
VALUES ($hostname, $cpu_number, $cpu_architecture, $cpu_model, $cpu_mhz, $l2_cache, $total_mem, $timestamp);"

psql -h localhost -U postgres -W

#function print_() {
#    echo -e "$1: $2\n"
#}
#print_ cpu_number "$cpu_number"
#print_ cpu_architecture "$cpu_architecture"
#print_ cpu_model "$cpu_model"
#print_ cpu_mhz "$cpu_mhz"
#print_ l2_cache "$l2_cache"

#put appropriate exit number
exit 0