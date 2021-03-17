#!/bin/bash

#Setup arguments
psql_host=$1 #localhost
psql_port=$2 #5432
db_name=$3 #host_agent
psql_user=$4 #postgres
psql_password=$5 #password

export PGPASSWORD=$psql_password

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

hostname=$(hostname -f)
disk_io_info=$(vmstat --unit Mvmstat --unit M)
memory_info=$(vmstat -t)
disk_info=$(df -BM)
timestamp=$(echo "$memory_info" | egrep -A1 "+UTC" | tail -n 1 | awk '{ print $18"\t"$19}' | xargs)
memory_free=$(echo "$memory_info"  | egrep -A1 "+free" | tail -n 1 | awk '{ print $4}' | xargs)
cpu_idle=$(echo "$disk_io_info"  | egrep -A1 "+id" | tail -n 1 | awk '{ print $15}' | xargs)
cpu_kernel=$(echo "$disk_io_info"  | egrep -A1 "+sy" | tail -n 1 | awk '{ print $14}' | xargs)
disk_io=$(echo "$disk_io_info"  | egrep -A1 "+bi" | tail -n 1 | awk '{print $9"/"$10}' | xargs)
disk_available=$(echo "$disk_info"  | egrep "^/dev/sda2" | awk '{print $4}' | xargs)

insert_stmt="INSERT INTO host_usage (time_, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES ('$timestamp', (SELECT id FROM host_info WHERE hostname='$hostname'), $memory_free, $cpu_idle, $cpu_kernel, '$disk_io', '$disk_available');"

check_table_info="SELECT EXISTS (SELECT FROM pg_tables WHERE tablename='host_info');"
check_table_usage="SELECT EXISTS (SELECT FROM pg_tables WHERE tablename='host_usage');"

function setup_psql() {
  psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -f ../sql/ddl.sql
}

function run_psql() {
  psql -t -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$1"
}

function add_row() {
  table01=$(run_psql "$check_table_info")
  table02=$(run_psql "$check_table_usage")
  if [ "$table01" == "f" ] || [ "$table02" == "f" ]
  then setup_psql
  else echo "TABLE EXISTS"
  fi

  run_psql "$insert_stmt"
}

add_row