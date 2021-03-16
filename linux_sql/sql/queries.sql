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
group by cpu_number
order by cpu_number asc total_mem desc;
