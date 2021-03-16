#!/bin/bash
# /Users/biznw/Documents/GitHub/jarvis_data_eng_YerinAn/linux_sql/scripts

hostname=$(hostname -f)
memory_info=$(cat /proc/meminfo)
timestamp=$(echo "$(date '+%Y-%m-%d %H:%M:%S' -u)" | awk '{print $1"\t"$2}' | xargs)
memory_free=$(echo "$memory_info"  | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_idle=$(echo "$memory_info"  | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_kernel=$(echo "$memory_info"  | egrep "^MemFree:" | awk '{print $2}' | xargs)
disk_io=$(echo "$memory_info"  | egrep "^MemFree:" | awk '{print $2}' | xargs)
disk_available=$(echo "$memory_info"  | egrep "^MemFree:" | awk '{print $2}' | xargs)

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