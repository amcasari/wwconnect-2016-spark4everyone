# wwconnect-2016-spark4everyone
[WW]Connect 2016 workshop: Apache Spark For Everyone


<h1>Installfest</h1>
----


<h2>Apache Zeppelin</h2>

* Interactive web-based notebook platform for data currently being incubated by Apache. 

* Multiple language backend including flavors of Spark, which means we don't have to install separate kernels, modules, plugins, or libraries to use it! Supports Scala(with Apache Spark), Python(with Apache Spark), SparkSQL, Hive, Markdown and Shell.

* Learn more at https://zeppelin.incubator.apache.org/

<h3>Installing Zeppelin at the command line</h3>

<h4>Clone the Repo</h4>

```git clone https://github.com/apache/incubator-zeppelin```

<h4>Build/Install</h4>

```mvn install -DXmx512m -DXX:MaxPermSize=256m -DskipTests -Dspark.version=1.6.0 -Dhadoop.version=2.4.0```

<h4>Start the Zep</h4>

```bin/zeppelin-daemon.sh start```

<h4>Open in browser</h4>

http://localhost:8080/

-----

<h2>RStudio</h2>

Spark includes SparkR after version 1.4(?), including a REPL called sparkR. It can also be used within interactive notebook environments -- such as RStudio

<h4>Download a prebuilt Spark release</h4>
http://spark.apache.org/downloads.html

<h4>You may need to install R</h4>
For Mac:
get the package here: https://cran.rstudio.com/bin/macosx/
or use a package manager like homebrew

For Windows:
?

<h4>Download RStudio Desktop Version</h4>
https://www.rstudio.com/products/rstudio/download/

----

<h2>Databricks Community Edition</h2>

A free version of databricks spark platform for learning. 


<h1>LINKS</h1>

Platforms and Downloads:

* zeppelin http://zeppelin.incubator.apache.org/
* RStudio http://www.rstudio.com/products/rstudio/download/
* Jupyter http://jupyter.org/

Spark Further:
* Spark Programming Guide: http://spark.apache.org/docs/latest/programming-guide.html
* StackOverflow: http://stackoverflow.com/questions/tagged/apache-spark
* Spark User List: http://www.mail-archive.com/user@spark.apache.org/






