// Databricks notebook source exported at Mon, 21 Mar 2016 17:30:23 UTC
// MAGIC %md 
// MAGIC <h1> Spark in scala For Everyone</h1>

// COMMAND ----------

// MAGIC %fs ls /databricks-datasets/bikeSharing/data-001

// COMMAND ----------

// MAGIC %md <h1> Load the data from file </h1>

// COMMAND ----------

val bikeRDD = sc.textFile("/databricks-datasets/bikeSharing/data-001/hour.csv",4)

// COMMAND ----------

bikeRDD.take(5).foreach(println)

// COMMAND ----------

// MAGIC %md <h4>filter out the header line</h4>

// COMMAND ----------

def isHeader(line: String): Boolean = {
 line.contains("instant")
}

// COMMAND ----------

val noHeaderBikeRDD = bikeRDD.filter(x => !isHeader(x))

// COMMAND ----------

// MAGIC %md <h1>Case Class</h1>

// COMMAND ----------

// MAGIC %md <h4>Scala Objects which come with useful override methods, and are automatically serializable</h4>

// COMMAND ----------

case class BikeData(date: String, season: String, temp: Double, casual: Integer, registered: Integer, counts: Integer)

// COMMAND ----------

def seasonNames(x: String): String = x match {
    case "1" => "winter"
    case "2" => "spring"
    case "3" => "summer"
    case "4" => "fall"
  }

def parse(line: String) = {
 val pieces = line.split(',')
 val date = pieces(1).toString
 val season = seasonNames(pieces(2)) 
 val temp = pieces(10).toDouble
 val casual = pieces(14).toInt
 val registered = pieces(15).toInt
 val counts = pieces(16).toInt
 BikeData(date, season, temp, casual, registered, counts)
}

// COMMAND ----------

// MAGIC %md <h4> When we use a method in a spark operation (like map), that method is packaged and sent out to all the executors, including any objects which it requires.</h4>

// COMMAND ----------

val parsedBikeRDD = noHeaderBikeRDD.map(line => parse(line))

// COMMAND ----------

// MAGIC %md <h1>Persistence Persistence Persistence</h1>

// COMMAND ----------

parsedBikeRDD.toDebugString

// COMMAND ----------

parsedBikeRDD.count

// COMMAND ----------

parsedBikeRDD.cache()

// COMMAND ----------

parsedBikeRDD.toDebugString

// COMMAND ----------

// MAGIC %md <h1>sortBy() -- involves a shuffle of all the data -- O(n log n)</h1>

// COMMAND ----------

val sorted = parsedBikeRDD.sortBy(bike => bike.counts, false)

// COMMAND ----------

sorted.take(5).foreach(println)

// COMMAND ----------

parsedBikeRDD.count

// COMMAND ----------

// MAGIC %md <h2> top()returns an array. It avoids sorting, so it is fast!! 
// MAGIC It makes one parallel pass through the data, collecting the top N in each partition in a heap, then merges the heaps.  -- O(n) </h2>

// COMMAND ----------

parsedBikeRDD.map(bd => bd.counts).top(5)

// COMMAND ----------

// MAGIC %md <h1>reduce() is an action</h1>

// COMMAND ----------

val total_counts = parsedBikeRDD.map(bd => bd.counts).reduce((a,b) => a + b )
val total_casual = parsedBikeRDD.map(bd => bd.casual).reduce((a,b) => a + b )
val total_registered = parsedBikeRDD.map(bd => bd.registered).reduce((a,b) => a + b )


// COMMAND ----------

// MAGIC %md <h1>stats() returns a class containing the count, mean, stdev, max, min of an RDD[Doubles]</h1>

// COMMAND ----------

val temperature_stats = parsedBikeRDD.map(bd => bd.temp).stats()
val counts_stats = parsedBikeRDD.map(bd => bd.counts.toDouble).stats()

// COMMAND ----------

// MAGIC %md <h1>PairedRDDs -- the power of Key,Value pairs</h1>
// MAGIC 
// MAGIC We want to calculate the count per season in our data, but the .stats() function doesnt break it out by group.  
// MAGIC We will use one of the aggregation methods for PairedRDDs
// MAGIC 
// MAGIC * combineByKey() -- has map-side combine. is used when return type from the aggregation is different from the values being aggregated (for example, running averages). It takes 3 functions:
// MAGIC createCombiner()
// MAGIC mergeValue()
// MAGIC mergeCombiner()
// MAGIC 
// MAGIC * reduceByKey()  -- has map-side combine
// MAGIC 
// MAGIC * groupByKey -- no map-side combine. Expensive.

// COMMAND ----------

//make a PairedRDD
val countsDF = parsedBikeRDD.map(bd => Tuple2(bd.season,bd.counts.toInt))

// COMMAND ----------

val seasonalCountsTotals = 
countsDF
.combineByKey(
(v) => (v, 1), 
(acc: (Int,Int), v) => (acc._1 + v, acc._2 + 1),
(acc1:(Int,Int), acc2:(Int,Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2) 
)

// COMMAND ----------

seasonalCountsTotals.collect()

// COMMAND ----------

// MAGIC %md <h4> Lets get an average temperature for the records of each season</h4>

// COMMAND ----------

//  create key value pairs 
val kvpair = parsedBikeRDD.map(line=>(line.season, (line.temp, 1)))

// COMMAND ----------

//  reducebyKey  to get  the sum of temps  and count sum. Transformations that may trigger a stage boundary typically accept a numPartitions argument 
val totalcounts = kvpair.reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2),2)  

// COMMAND ----------

totalcounts.count

// COMMAND ----------

val avgs =  totalcounts.mapValues{ case (total, count) => total.toDouble / count } 

// COMMAND ----------

avgs.collect

// COMMAND ----------

// MAGIC %md <h1>Spark SQL!!!</h1>

// COMMAND ----------

// MAGIC %md <h4>Alternately to working wih <K,V> pairs, we can read the csv directly into a SparkSQL DataFrame</h4>

// COMMAND ----------

val bikeDF = sqlContext.read.format("com.databricks.spark.csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/bikeSharing/data-001/hour.csv")

// COMMAND ----------

// MAGIC %md <h4> Databricks Cloud has some extra graphical features which are specific to it's notebooks, such as display() for Spark DataFrames.</h4>

// COMMAND ----------

display(bikeDF)

// COMMAND ----------

// MAGIC %md <h4> One of the strengths of DataFrames is the ability to register them as tables which are available to any application using the same sqlContext. You can run sql queries on them. </h>

// COMMAND ----------

bikeDF.registerTempTable("bikes")
val holidays = sqlContext.sql("SELECT dteday, season,temp,casual,registered,cnt FROM bikes WHERE holiday LIKE '1'")

// COMMAND ----------

// MAGIC %md <h4> When we look at the same bar plot of just the holiday rentals, the ratio of casual to registered bike rentals appears generally larger.</h4>

// COMMAND ----------

display(holidays)

// COMMAND ----------

val nn = holidays.groupBy("season").sum("cnt","casual","registered")

// COMMAND ----------

display(nn)

// COMMAND ----------


