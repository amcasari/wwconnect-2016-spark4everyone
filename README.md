# wwconnect-2016-spark4everyone
[WW]Connect 2016 workshop: Apache Spark For Everyone


<h2>Apache Zeppelin</h2>

Zeppelin is an interactive web-based notebook platform which is now an Apache Incubator Project. It has a multiple language backend including flavors of Spark, which means we don't have to install separate kernels, modules, plugins, or libraries to use it!

Currently Zeppelin supports Scala(with Apache Spark), Python(with Apache Spark), SparkSQL, Hive, Markdown and Shell.

<h3>Installing Zeppelin</h3>
* clone the repo
```git clone https://github.com/apache/incubator-zeppelin```

* Build/Install
```mvn install -DXmx512m -DXX:MaxPermSize=256m -DskipTests -Dspark.version=1.6.0 -Dhadoop.version=2.4.0```

* Start Zeppelin
```bin/zeppelin-daemon.sh start```

* Open in browser
```http://localhost:8080/```




