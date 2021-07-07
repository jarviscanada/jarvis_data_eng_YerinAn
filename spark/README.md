# Introduction
Because of the success of the Python Data Wrangling PoC project, the LGS company decided to invest more money into the data engineering project and implement the same data strategy across the entire company. This project chose to apply the data solution with Apache Spark, which allows parallel processing of large amounts of data on a cluster because the exiting Jupyter Notebook and Python solution cannot handle a large dataset as it runs on a single machine. We were using Google Cloud Platform/Zeppelin and Microsoft Azure/Databricks to evaluate data.
-   Evaluating Spark using Zeppelin Notebook running on Hadoop
-   Evaluating Spark using Databricks running on Azure
For the Zeppelin/Hadoop evaluation, I used a 3-node cluster on GCP Dataproc, the 2016 World Development Indicators dataset, Spark SQL and DataFrames (Spark Structured APIs) Zeppelin notebook to analyze the data. For the Databricks/Azure analysis, I re-implemented the Jupyter Notebook from the Data Analytics with Pandas/Jupyter PoC project using Azure Databricks, an Apache Spark-based analytics platform for the Microsoft Azure cloud services.

# Databricks and Hadoop Implementation
I implemented notebook using the Azure Databricks, an Apache Spark-based analytics platform optimized for Microsoft Azure cloud services. To do that, I extracted a CSV file from the PostgreSQL instance and loaded it onto the Databricks file system to register a table. By default, Spark uses a Hive metastore with a Derby database to work with database objects. Using SparkSQL and Scala, I implemented parallel data analysis on a cluster to answer several business questions, divide customers into segments, and identify important segments to help LGS develop targeted marketing strategies.
Set up Azure Databricks environment:
1. Extracted the retail data CSV file from PostgreSQL.
2. Created to Microsoft Azure and gain access to an Azure Databricks workspace
3. Created a data cluster with the Spark 2.14 version and run the cluster.
4. Ingested the retail data CSV file into Azure Storage to create a table
5. Created a new Databricks Notebook with Scala.
#### Architecture
![image_description](assets/Azure.png)
Databricks uses Apache-Spark based platform that runs a distributed system behind the scenes, meaning the workload is automatically split across various processors and scales up and down on demand. Increased efficiency results in direct time and cost savings for massive tasks. Moreover, Databricks provides a notebook development environment complete with an underlying implementation of Apache Spark on the automatically provisioned data clusters. 
Azure Databricks Workspace: it is a manifestation of the Databricks application installed on Azure cloud computing resources. Databricks UI was used to create the cluster, store the retail data .csv into Databricks File System (DBFS), and create a notebook. Spark uses a Hive Metastore by default to work with database objects. The data was imported in the Databricks notebook with the above-defined schema into a DataSet and then persisted to a table to run SQL queries if needed.
The Azure Virtual Machines: These are based around the Spark architecture. The Spark session interacts with the cluster manager in charge of manager jobs and allocates resources to applications in the driver. Worker nodes took multiple tasks from the manager, executed the work assigned, and reported the computation back to the Spark session.
#### Databricks
![image_description](assets/Retail_Data_Spark.png)

# Zeppelin and Hadoop Implementation
The project has the Hadoop cluster consisted a master node and two subordinate worker nodes using GCP Dataproc. All three nodes have the same hardware specifications: 2 vCPUs, 12GB RAM and 100GB HDD. And Dataproc uses image versions to bundle operating-system, big data components, and GCP connectors into one package deployed on a cluster. Data was loaded onto the Hadoop cluster and created a Hive table from it in PARQUET format for optimization purposes. Hive holds metadata that is table schema, HDFS location, etc. It uses it to represent data in the underlying distributed file system as database objects. And then, Zeppelin provides data ingestion, discovery and analytics through scala/data-processing backend plugins.m
#### Architecture
![image_description](assets/hadoop_cluster.png)
#### Zeppelin
![image_description](assets/sparkDataframe.png)

# Future Improvement
- Create RFM segmentation categories
- Evaluate Spark on other environments for comparison
- Explore more of Apache Spark(GraphX, Spark Streaming and so on)
