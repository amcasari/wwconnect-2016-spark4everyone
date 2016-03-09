# wwconnect-2016-spark4everyone
[WW]Connect 2016 workshop: Apache Spark For Everyone

<h1>Installfest</h1>

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

```here: http://localhost:8080/```

-----

<h2>SparkR with RStudio</h2>

* SparkR comes bundled with Spark after version 1.4. It comes with a REPL called sparkR. It can also be used within interactive notebook environments such as RStudio

<h4>Download a prebuilt Spark release</h4>
```here: http://spark.apache.org/downloads.html
	For the purpose of this workshop select:
	Spark release: 1.6.0
	Package type: prebuilt for Hadoop 1.4 and later````

