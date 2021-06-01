-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * FROM retail limit 10;

-- Check # of records
SELECT COUNT(*) FROM retail;

-- number of clients (e.g. unique client ID)
SELECT COUNT(DISTINCT CUSTOMER_ID) AS COUNT
FROM RETAIL 
WHERE CUSTOMER_ID NOTNULL;

-- invoice date range (e.g. max/min dates)
SELECT 
MAX(INVOICE_DATE) AS MAX,
MIN(INVOICE_DATE) AS MIN
FROM RETAIL;

-- number of SKU/merchants (e.g. unique stock code)
SELECT COUNT(DISTINCT STOCK_CODE) FROM RETAIL;

-- Calculate average invoice amount excluding invoices with a negative amount 
-- (e.g. canceled orders have negative amount)
SELECT
AVG(INVOICE_COST)
FROM
    (SELECT 
    SUM(QUANTITY * UNIT_PRICE) AS INVOICE_COST,
    INVOICE_NO
    FROM RETAIL
    GROUP BY INVOICE_NO
    HAVING SUM(QUANTITY * UNIT_PRICE) > 0) AS INVOICE;

-- Calculate total revenue (e.g. sum of unit_price * quantity)
SELECT SUM(UNIT_PRICE * QUANTITY) FROM RETAIL;

-- Calculate total revenue by YYYYMM 
SELECT to_char(INVOICE_DATE,'YYYYMM') AS INVOICE_MONTH,
    SUM(UNIT_PRICE * QUANTITY)
FROM RETAIL
GROUP BY INVOICE_MONTH
ORDER BY INVOICE_MONTH ASC;