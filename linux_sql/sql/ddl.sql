--01. (optional) switch to `host_agent`
\c host_agent;
--02. create `host_info` table if not exist create `host_info` table if not exist
CREATE TABLE IF NOT EXISTS host_info (
  id SERIAL NOT NULL,
  hostname VARCHAR NOT NULL,
  cpu_number INTEGER,
  cpu_architecture VARCHAR,
  cpu_model VARCHAR,
  cpu_mhz NUMERIC(10,3),
  L2_cache VARCHAR,
  total_mem INTEGER,
  time_ timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
  PRIMARY KEY (id),
  UNIQUE (hostname)
);
--INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, time_)
--VALUES ('hostname04', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300.000, 256, 751324, '2021-03-16 16:58:58');

--03. create `host_usage` table if not exist
CREATE TABLE IF NOT EXISTS host_usage (
  time_ timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
  host_id SERIAL NOT NULL,
  memory_free INTEGER,
  cpu_idle INTEGER,
  cpu_kernel INTEGER,
  disk_io VARCHAR,
  disk_available VARCHAR,
  CONSTRAINT fk_host_info
        FOREIGN KEY(host_id)
  	  REFERENCES host_info(id)
);
--INSERT INTO host_usage (time_, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
--VALUES (CURRENT_TIMESTAMP, 1, 256, 95, 0, 0, 31220);

--# execute .spl
--psql -h localhost -U postgres -d host_agent -f sql/ddl.sql