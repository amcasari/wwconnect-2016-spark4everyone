Sys.setenv(SPARK_HOME="/Users/deborahsiegel/software/spark-1.6.0-bin-hadoop2.4")
.libPaths(c(file.path(Sys.getenv("SPARK_HOME"), "R", "lib"), .libPaths()))
setwd("~/data/")
library(SparkR)
sc <- sparkR.init(master="local[2]",appName="SparkR-for-everyone",sparkPackages="com.databricks:spark-csv_2.11:1.2.0")
sqlContext <- sparkRSQL.init(sc)

###########
library(magrittr)
#library(help="SparkR")
###########

bikeDF <- read.df(sqlContext, "bikes/", "com.databricks.spark.csv",header="true",inferSchema="true")
head(bikeDF)
printSchema(bikeDF)
show(bikeDF)

###########

cache(bikeDF)
count(bikeDF)

summarize(groupBy(bikeDF, bikeDF$season), count = n(bikeDF$cnt)) %>% head

crosstab(bikeDF,"season","mnth")

sortedBikes <- arrange(bikeDF, "cnt", decreasing = TRUE)
selectedBikes <- select(sortedBikes, sortedBikes$season, sortedBikes$holiday, sortedBikes$registered, sortedBikes$casual, sortedBikes$cnt)

filteredBikes <- filter(selectedBikes, selectedBikes$holiday == 1)
head(filteredBikes)
count(filteredBikes)

hh <- collect(filteredBikes)

hist(hh$cnt,
     main="Histogram for Holiday Bike Rental Counts", 
     xlab="Number of Rentals", 
     col="purple",
     xlim=c(100,600))

# back to the full data in bikeRDD
model <- glm(cnt ~ temp + windspeed + hum + windspeed + holiday + season, family="gaussian",
             data = bikeDF)
summary(model)
preds <- predict(model, bikeDF)

errors <- select(
  preds, preds$label, preds$prediction, preds$season,
  alias(preds$label - preds$prediction, "error"))

head(errors)


#sparkR.stop()
