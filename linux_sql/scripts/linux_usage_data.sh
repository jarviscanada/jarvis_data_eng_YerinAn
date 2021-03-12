#!/bin/bash
# /Users/biznw/Documents/GitHub/jarvis_data_eng_YerinAn/linux_sql/scripts

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

function print_() {
    echo -e "$1: $2\n"
}

#list=( 'hostname' 'cpu_number' 'cpu_architecture' 'cpu_model' 'cpu_mhz' 'l2_cache' 'memory_info' 'total_mem' 'timestamp' )
#for i in "${list[@]}"
#do
#  print_ "$i"
#done

print_ hostname "$hostname"
print_ cpu_number "$cpu_number"
print_ totalmemory "$total_mem"
print_ timestamp "$timestamp"
