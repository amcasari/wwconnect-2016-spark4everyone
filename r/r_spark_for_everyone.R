# Setup for SparkR to run in RStudio
Sys.setenv(SPARK_HOME="/Users/deborahsiegel/software/spark-1.6.0-bin-hadoop2.4")
.libPaths(c(file.path(Sys.getenv("SPARK_HOME"), "R", "lib"), .libPaths()))
setwd("~/data/")
library(SparkR)

# Create Spark Context and SQL Context
sc <- sparkR.init(master="local[2]",appName="SparkR-for-everyone",sparkPackages="com.databricks:spark-csv_2.11:1.2.0")
sqlContext <- sparkRSQL.init(sc)

###########
library(magrittr)
#library(help="SparkR")
###########

# Use spark-csv package to read a csv file into a Spark DataFrame
bikeDF <- read.df(sqlContext, "bikes/", "com.databricks.spark.csv",header="true",inferSchema="true")
head(bikeDF)
printSchema(bikeDF)
show(bikeDF)

# Persistence!

cache(bikeDF)
count(bikeDF)

# Group the data by season, and then summarize with the sum of the counts per season
# These are both transformations
# Use magrittr to pipe the result (a DAG) directly into an action

summarize(groupBy(bikeDF, bikeDF$season), count = n(bikeDF$cnt)) %>% head

# The seasons are coded with integers 1-4. Which seasons do these correspond with?
# Use crosstab() action

crosstab(bikeDF,"season","mnth")

# arrange() is a transformation which will sort our DataFrame. 
# select() is a transformation to select columns.
# filter() is a transformation to select rows.

sortedBikes <- arrange(bikeDF, "cnt", decreasing = TRUE)
selectedBikes <- select(sortedBikes, sortedBikes$season, sortedBikes$holiday, 
                        sortedBikes$registered, sortedBikes$casual, sortedBikes$cnt)
filteredBikes <- filter(selectedBikes, selectedBikes$holiday == 1)

# Actions will cause the above data transformations to happen on the executors. 
head(filteredBikes,3)
count(filteredBikes)

# Since our data is not too big, we can bring all of it over to our driver from the executors
# into an r data.frame

localDF <- collect(filteredBikes)

# And do any local R visualizations or analyses on it
hist(localDF$cnt,
     main="Histogram for Holiday Bike Rental Counts", 
     xlab="Number of Rentals", 
     col="purple",
     xlim=c(100,600))

# back to the full data in bikeRDD. 
# SparkR developers are working on building out sparkR machine learning algorithms
# Here is a generalized linear model, a linear regression on the data to predict the 
# number of bikes rented on a given day

model <- glm(cnt ~ temp + windspeed + hum + windspeed + holiday + season, family="gaussian",
             data = bikeDF)
summary(model)
preds <- predict(model, bikeDF)

errors <- select(
  preds, preds$label, preds$prediction, preds$season,
  alias(preds$label - preds$prediction, "error"))

head(errors)


sparkR.stop()
