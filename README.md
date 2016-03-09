# wwconnect-2016-spark4everyone
[WW]Connect 2016 workshop: Apache Spark For Everyone


<h2>Apache Zeppelin</h2>


* Interactive web-based notebook platform currently being incubated by Apache. 

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

```http://localhost:8080/```




