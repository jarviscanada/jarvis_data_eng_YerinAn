#! /bin/bash

#Setup arguments
psql_host=$1 #localhost
psql_port=$2 #5432
db_name=$3 #host_agent
psql_user=$4 #postgres
psql_password=$5 #password

##validate arguments
if [ "$#" -ne 5 ]; then
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
total_mem=$(echo "$memory_info"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(echo "$(date '+%Y-%m-%d %H:%M:%S' -u)" | awk '{print $1"\t"$2}' | xargs)

insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, '$l2_cache', $total_mem, '$timestamp');"

export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -f ../sql/ddl.sql -c "$insert_stmt"

exit 0

#bash host_info.sh localhost 5432 host_agent postgres password
