--01. (optional) switch to `host_agent`
\c host_agent;
--cpu_number,host_id,total_mem
--host_info: (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
--host_usage: (time_, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
SELECT
cpu_number,
id as host_id,
total_mem
FROM host_info
order by cpu_number asc, total_mem desc;

--host_id, host_name, timestamp,avg_used_mem_percentage
--Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory)
SELECT
(i.total_mem-u.memory_free)
FROM
host_usage u
left join host_info i
on i.id = u.host_id

select
dateadd(minute, datediff(minute, 0, u.time_), 0) as avg_used_mem,
avg(i.total_mem-u.memory_free) as  avg_used_mem_percentage
from host_usage u
left join host_info i
on i.id = u.host_id
group by dateadd(minute, datediff(minute, 0, u.time_), 0)

select Time_Stamp_Hour_Minute = convert(varchar(5),u.time_,8)
      , AvgValue = avg(Value)
from host_usage u
left join host_info i
on i.id = u.host_id
group by convert(varchar(5),u.time_,8)

SELECT floor(timestamp/(60 * 1000)) as timestamp_minute,
       avg(value)
FROM host_usage u
     left join host_info i
     on i.id = u.host_id
GROUP BY timestamp_minute;


SELECT date_trunc('hour', time_) + date_part('minute', time_):: int / 5 * interval '5 min'
FROM host_usage;

--################################################
SELECT MIN(u.time_), AVG(i.total_mem-u.memory_free)
FROM host_usage u
left join host_info i
on i.id = u.host_id
GROUP BY date_trunc('hour', u.time_),
    FLOOR(date_part('minute', u.time_)/12)
--################################################

SELECT
u.host_id,
i.hostname as host_name,
u.time_ as timestamp,
(SELECT )as avg_used_mem_percentage
FROM
host_usage u
left join host_info i
on i.id = u.host_id