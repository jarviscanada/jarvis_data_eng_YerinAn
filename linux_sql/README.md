```markdown
Note: You are NOT allowed to copy any content from the scrum board, including text, diagrams, code, etc.. Your Github will be visible and shared with Jarvis clients, so you have to create unique content that impress your future boss😎.

# Introduction
(about 150-200 words)
Discuss the design of the project. What does this project/product do? Who are the users? What are the technolgoies you have used? (e.g. bash, docker, git, etc..)

# Quick Start
Use markdown code block for your quick start commands
- Start a psql instance using psql_docker_01.sh create/start/stop/remove/check
- Create tables using psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
- Insert hardware specs data into the db using host_info.sh psql_host port
- Insert hardware usage data into the db using host_usage.sh
- Crontab setup

# Implemenation
Discuss how you implement the project.
## Architecture
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to `assets` directory.

## Scripts
Shell script descirption and usage (use markdown code block for script usage)
1. psql_docker.sh
   1. DESCRIPTION : depending on the command, run a shell script.
   1. COMMAND     : command='create' 'start' 'stop' 'remove' 'check'
   1. HOW TO RUN  : bash psql_docker.sh start password yerin
1. host_info.sh
   1. DESCRIPTION : checking the table exists or not, and adding a row
   1. HOW TO RUN  : bash host_info.sh localhost 5432 host_agent postgres password run
1. host_usage.sh
   1. DESCRIPTION : checking the table exists or not, and adding a row
   1. HOW TO RUN  : bash host_usage.sh localhost 5432 host_agent postgres password run

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`
- `ddl`
- `queries`
* host_info
id | hostname | cpu_number | cpu_architecture | cpu_model | cpu_mhz | L2_cache | total_mem | time_ 
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | -------------
1 | hostname | 1 | x86_64 | Intel(R) Xeon(R) CPU @ 2.30GHz | 2300.000 | 256 | 751324 | '2021-03-16 16:58:58' 

* host_usage
time_ | host_id | memory_free | cpu_idle | cpu_kernel | disk_io | disk_available
------------ | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- 
2021-03-16 18:26:58 | 1 | 30000 | 95 | 0 | 45 | 23439 

# Test
How did you test your bash scripts and SQL queries? What was the result?
- `host_info` : set up the information about host information and add a row.
- `host_usage`: set up the information about host usage and add a row.
- `ddl`       : create tables which are host_info, and host_usage.
- `queries`   : able to run some queries
                (Total memory info, Average memory usage, Detect host failure) 
                and show the results.
# Improvements
Write at least three things you want to improve 
- create exception handler.
- write explanations for all functions.
- create a file to run all functions at once.
```