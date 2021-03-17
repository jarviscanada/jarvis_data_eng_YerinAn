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
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
LANGUAGE PLPGSQL;

--Average memory usage
SELECT DISTINCT
    u.host_id,
    i.hostname AS host_name,
    round5(u.time_) AS timestamp,
    (SELECT
    ROUND((i1.total_mem - AVG(u1.memory_free))/i1.total_mem * 100, 2) AS num
    FROM host_usage u1
    INNER JOIN host_info i1
    ON i1.id = u1.host_id
    WHERE round5(u1.time_) = round5(u.time_)
    AND i1.id = i.id
    GROUP BY round5(u1.time_),i1.total_mem) AS avg_used_mem_percentage
FROM host_usage u
INNER JOIN host_info i
ON i.id = u.host_id;

--Detect host failure
SELECT
    n2.host_id,
    n2.timestamp,
    num_data_points
FROM (SELECT
    u1.host_id,
    round5(u1.time_) AS t_n1,
    count(*) as num_data_points
    FROM host_usage u1
    GROUP BY round5(u1.time_), u1.host_id
    HAVING count(*)<5) AS n1
INNER JOIN (SELECT
    host_id,
    MIN(time_) AS timestamp
    FROM host_usage
    GROUP BY round5(time_), host_id) AS n2
ON round5(n2.timestamp)=t_n1
WHERE n2.host_id=n1.host_id
ORDER BY n2.host_id, n2.timestamp;

--# execute .spl
--psql -h localhost -U postgres -d host_agent -f sql/queries.sql