#!/bin/bash
# /Users/biznw/Documents/GitHub/jarvis_data_eng_YerinAn/linux_sql/scripts

hostname=$(hostname -f)
memory_info=$(cat /proc/meminfo)
total_mem=$(echo "$memory_info"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(echo "$(date '+%Y-%m-%d %H:%M:%S' -u)" | awk '{print $1"\t"$2}' | xargs)

function print_() {
    echo -e "$1: $2\n"
}

print_ hostname "$hostname"
print_ totalmemory "$total_mem"
print_ timestamp "$timestamp"


#list=( 'hostname' 'cpu_number' 'cpu_architecture' 'cpu_model' 'cpu_mhz' 'l2_cache' 'memory_info' 'total_mem' 'timestamp' )
#for i in "${list[@]}"
#do
#  print_ "$i"
#done