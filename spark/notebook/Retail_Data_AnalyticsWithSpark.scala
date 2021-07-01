// Databricks notebook source
var source = spark.read.table("retail_csv")
source.printSchema()

// COMMAND ----------

display(source.limit(10))

// COMMAND ----------

import org.apache.spark.sql.functions.to_date
var retail_df = source.withColumn("invoice_date", to_date($"invoice_date", "yyyy-MM-dd"))
display(retail_df)

// COMMAND ----------

retail_df.summary().show()

// COMMAND ----------

//register sql
source.createOrReplaceTempView("retail")

// COMMAND ----------

// MAGIC %sql
// MAGIC SELECT * FROM retail LIMIT 10

// COMMAND ----------

// MAGIC %md
// MAGIC ## TOTAL INVOICE AMOUNT DISTRIBUTION

// COMMAND ----------

var total_amount_df = retail_df.withColumn("amount", $"quantity" * $"unit_price")
                   .filter($"amount" > 0)
                   .groupBy("invoice_no").sum("amount")
total_amount_df = total_amount_df.withColumnRenamed("sum(amount)", "total_amount")
display(total_amount_df.orderBy("invoice_no").limit(10))

// COMMAND ----------

// MAGIC %md
// MAGIC #### DISTRIBUTION

// COMMAND ----------

display(total_amount_df.describe("total_amount"))

// COMMAND ----------

// MAGIC %md
// MAGIC #### Distribution without outliers

// COMMAND ----------

var quantile = total_amount_df.stat.approxQuantile("total_amount", Array(0.85), 0)
var remove_outliers = quantile(0)
print(remove_outliers)

// COMMAND ----------

val no_outliers_df = total_amount_df.filter($"total_amount" < remove_outliers)
display(no_outliers_df.describe("total_amount"))

// COMMAND ----------

display(no_outliers_df.orderBy("invoice_no").limit(10))

// COMMAND ----------

// MAGIC %md
// MAGIC ## MONTHLY PLACED AND CANCELLED ORDERS

// COMMAND ----------

import org.apache.spark.sql.functions._
var orders_df = retail_df
                .withColumn("YYYYMM", date_format($"invoice_date", "yyyyMM"))
                .groupBy("invoice_no", "YYYYMM")
                .count()
display(orders_df.orderBy("YYYYMM").limit(10))

// COMMAND ----------

var cancelled_df = orders_df
                  .withColumn("cancellation", $"invoice_no".startsWith("C"))
cancelled_df.show()

// COMMAND ----------

var placed_count_df = cancelled_df.filter($"cancellation" === false)
placed_count_df = placed_count_df.select("YYYYMM", "cancellation")
                  .groupBy("YYYYMM")
                  .count()
                  .orderBy("YYYYMM")
                  .withColumnRenamed("count", "placed")
placed_count_df.show()

// COMMAND ----------

var cancelled_count_df = cancelled_df.filter($"cancellation" === true)
cancelled_count_df = cancelled_count_df.select("YYYYMM", "cancellation")
.groupBy("YYYYMM")
.count()
.orderBy("YYYYMM")
.withColumnRenamed("count", "cancelled")
display(cancelled_count_df.limit(10))

// COMMAND ----------

var merge_count_df = placed_count_df
                     .join(cancelled_count_df, "YYYYMM")
display(merge_count_df.orderBy("YYYYMM"))

// COMMAND ----------

display(merge_count_df.orderBy("YYYYMM"))

// COMMAND ----------

// MAGIC %md
// MAGIC ## MONTHLY SALES

// COMMAND ----------

var monthly_sales_df = retail_df
.withColumn("amount", $"quantity" * $"unit_price")
.withColumn("YYYYMM", date_format($"invoice_date", "yyyyMM"))
.groupBy("YYYYMM")
.sum("amount")
.withColumnRenamed("sum(amount)", "monthly_sales")

display(monthly_sales_df.orderBy("YYYYMM").limit(10))

// COMMAND ----------

display(monthly_sales_df.orderBy("YYYYMM"))

// COMMAND ----------

// MAGIC %md
// MAGIC ## MONTHLY SALES GROWTH

// COMMAND ----------

import org.apache.spark.sql.expressions.Window

var pre_win = Window.partitionBy().orderBy("YYYYMM")
var monthly_growth_df = monthly_sales_df
.withColumn("pre_val", lag(monthly_sales_df("monthly_sales"), 1).over(pre_win))

monthly_growth_df.show()

// COMMAND ----------

monthly_growth_df = monthly_growth_df.withColumn("growth", ($"monthly_sales" - $"pre_val") / $"pre_val" * 100 )
monthly_growth_df.show()

// COMMAND ----------

display(monthly_growth_df)

// COMMAND ----------

// MAGIC %md
// MAGIC ## MONTHLY ACTIVE USERS

// COMMAND ----------

var monthly_active_users_df = retail_df
.withColumn("YYYYMM", date_format($"invoice_date", "yyyyMM"))
.groupBy("YYYYMM")
.agg(countDistinct("customer_id"))
.withColumnRenamed("count(DISTINCT customer_id)", "active_users")

monthly_active_users_df.orderBy("YYYYMM").show(10)

// COMMAND ----------

display(monthly_active_users_df.orderBy("YYYYMM"))

// COMMAND ----------

// MAGIC %md
// MAGIC ## NEW AND EXISTING USERS

// COMMAND ----------

var monthly_new_users_df = retail_df
.withColumn("YYYYMM", date_format($"invoice_date", "yyyyMM"))
.groupBy("customer_id")
.agg(min("YYYYMM"))
.withColumnRenamed("min(YYYYMM)", "YYYYMM")
.groupBy("YYYYMM")
.count()
.withColumnRenamed("count", "new_users")

display(monthly_new_users_df.orderBy("YYYYMM").limit(10))

// COMMAND ----------

var existing_user_df = monthly_active_users_df
.join(monthly_new_users_df, "YYYYMM")
.withColumn("existing_user", $"active_users" - $"new_users" + 1)
.drop("active_users")
.orderBy("YYYYMM")

// COMMAND ----------

display(existing_user_df)

// COMMAND ----------

// MAGIC %md
// MAGIC ## RFM SEGMENT

// COMMAND ----------

var tmp_retail_df = retail_df
.withColumn("YYYYMM", date_format($"invoice_date", "yyyyMM"))
.withColumn("today", to_date(lit("2012-01-01")))

display(tmp_retail_df.limit(10))

// COMMAND ----------

// MAGIC %md
// MAGIC #### RECENCY

// COMMAND ----------

var recency_date = tmp_retail_df
.groupBy("customer_id")
.agg(max("invoice_date"), max("today"))
.withColumnRenamed("max(invoice_date)", "invoice_date")
.withColumnRenamed("max(today)", "today")

var recency_df = recency_date.select($"customer_id", datediff($"today", $"invoice_date").as("recency"))
.filter($"customer_id".isNotNull)
.orderBy("customer_id")

recency_df.show(10)

// COMMAND ----------

// MAGIC %md
// MAGIC #### FREQUENCY

// COMMAND ----------

var frequency_df = tmp_retail_df.groupBy("customer_id")
.agg(countDistinct("invoice_no"))
.withColumnRenamed("count(DISTINCT invoice_no)", "frequency")
.orderBy("customer_id")
.filter($"customer_id".isNotNull)

frequency_df.show(10)

// COMMAND ----------

// MAGIC %md
// MAGIC #### MONETARY

// COMMAND ----------

var monetary_df = retail_df
.withColumn("amount", $"quantity" * $"unit_price")
monetary_df = monetary_df
.select("customer_id", "amount")
.groupBy("customer_id")
.agg(sum("amount"))
.withColumnRenamed("sum(amount)", "monetary")
.filter($"customer_id".isNotNull)

display(monetary_df.orderBy("customer_id").limit(10))

// COMMAND ----------

var rfm_tmp_df = recency_df
.join(frequency_df, "customer_id")
.join(monetary_df, "customer_id")
.orderBy("customer_id")

display(rfm_tmp_df)

// COMMAND ----------


