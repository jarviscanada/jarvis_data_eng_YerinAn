#! /bin/bash

#Setup arguments
psql_host=$1 #localhost
psql_port=$2 #5432
db_name=$3 #host_agent
psql_user=$4 #postgres
psql_password=$5 #password

export PGPASSWORD=$psql_password

##validate arguments
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

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

check_hostname="SELECT EXISTS (SELECT id FROM host_info WHERE hostname='$hostname');"
check_table="SELECT EXISTS (SELECT FROM pg_tables WHERE tablename='host_info');"

#SELECT EXISTS (SELECT id FROM host_info WHERE hostname='host90')
function setup_psql() {
  psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -f ../sql/ddl.sql
}

function run_psql() {
  psql -t -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$1"
}

function add_row() {
  table=$(run_psql "$check_table")
  if [ "$table" == "f" ]
  then setup_psql
  else echo "TABLE EXISTS"
  fi

  get=$(run_psql "$check_hostname")
  if [ "$get" == " f" ]
    then run_psql "$insert_stmt"
    else
      echo "HOST EXISTS"
  fi
}

add_row

exit 0
#bash host_info.sh localhost 5432 host_agent postgres password run

#parse hardware specification
#hostname=$(hostname -f)
#lscpu_out=`lscpu`
#cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
#cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
#cpu_model=$(echo "$lscpu_out"  | egrep "^Model:" | awk '{print $2}' | xargs)
#cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU\sMHz" | awk '{print $3}' | xargs)
#l2_cache=$(echo "$lscpu_out"  | egrep "^L2\scache:" | awk '{print $3}' | xargs)
#memory_info=$(cat /proc/meminfo)
#total_mem=$(echo "$memory_info"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
#timestamp=$(echo "$(date '+%Y-%m-%d %H:%M:%S' -u)" | awk '{print $1"\t"$2}' | xargs)
#
#insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
#VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, '$l2_cache', $total_mem, '$timestamp');"
#
#check_hostname="SELECT EXISTS (SELECT id FROM host_info WHERE hostname='$hostname')::int;"

#insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
#VALUES ('host9', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 751324, '2021-03-17 16:58:58');"

#insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
#VALUES ('$hostname', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 751324, '2021-03-17 16:58:58');"
#check_hostname="SELECT EXISTS (SELECT id FROM host_info WHERE hostname='halo')::int;"
#check_hostname="SELECT EXISTS (SELECT id FROM host_info WHERE hostname='$hostname')::int;"
#SELECT EXISTS (SELECT 1 FROM host_info WHERE hostname='hostname07')::int