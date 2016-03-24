# wwconnect-2016-spark4everyone
[WW]Connect 2016 workshop: Apache Spark For Everyone


<h1>Installfest</h1>
----

<h2>Spark</h2>

<h4>Download a prebuilt Spark release</h4>
http://spark.apache.org/downloads.html

-----
<h2>INTERACTIVE NOTEBOOKS</h2>
This section contains steps to install Apache Zeppelin, RStudio, Databricks Community Edition, and Jupyter.

<h3>Apache Zeppelin</h3>

* Interactive web-based notebook platform for data currently being incubated by Apache. 

* Multiple language backend including flavors of Spark, which means we don't have to install separate kernels, modules, plugins, or libraries to use it! Supports Scala(with Apache Spark), Python(with Apache Spark), SparkSQL, Hive, Markdown and Shell.

* Learn more at https://zeppelin.incubator.apache.org/

Installing Zeppelin at the command line:

<h4>Clone the Repo</h4>

```git clone https://github.com/apache/incubator-zeppelin```

<h4>Build/Install</h4>

```mvn install -DXmx512m -DXX:MaxPermSize=256m -DskipTests -Dspark.version=1.6.0 -Dhadoop.version=2.4.0```

<h4>Start the Zep</h4>

```bin/zeppelin-daemon.sh start```

<h4>Open in browser</h4>

http://localhost:8080/

<h4>For now, need to run a dependency cell as the very first cell, if you want access to spark-csv package</h4>
<pre>
%dep
z.reset()
z.addRepo("Spark Packages Repo").url("http://dl.bintray.com/spark-packages/maven")
z.load("com.databricks:spark-csv_2.11:1.4.0")
</pre>

-----

<h3>Jupyter</h3>

While Jupyter runs code in many different programming languages, Python is a prerequisite for installing Jupyter notebook.

<h4>Download Jupyter as part of Anaconda Python distribution</h4>
https://www.continuum.io/downloads

<h4>Jupyter Project installs for most platforms:</h4>
http://jupyter.readthedocs.org/en/latest/install.html

You can also get jupyter with the anaconda tool 'conda'

```conda install jupyter```

or if you don't have anaconda, you can use pip. eg:

```pip3 install jupyter``` 
or
```pip install jupyter``` 


<h4>Now you get to set up jupyter to run with a pyspark kernel. Here is one way to do it, this example is with Jupyter 4.1.0, spark-1.6.1-bin-hadoop2.6</h4>
1. Create a profile 
...<pre>ipython profile create pyspark</pre>

2. cd into ~/.ipython/profile_pyspark and edit the file ipython_config.py
...<pre>
c.NotebookApp.ip = '*'
c.NotebookApp.open_browser = False
c.NotebookApp.port = 8880 # or whatever you want; be aware of conflicts with CDH
</pre>

3. Now creat a file ~/.ipython/profile_pyspark/startup/00-pyspark-setup.py and write this in. Note that the py4j file will be whichever specific one you fine in SPARK_HOME/python/lib :
...<pre>
import os
import sys
spark_home = os.environ.get('SPARK_HOME', None)
  if not spark_home:
      raise ValueError('SPARK_HOME environment variable is not set')
sys.path.insert(0, os.path.join(spark_home, 'python'))
  sys.path.insert(0, os.path.join(spark_home, 'python/lib/py4j-0.9-src.zip'))
execfile(os.path.join(spark_home, 'python/pyspark/shell.py'))
</pre>

4. Now make sure you have some environmental variables set, either in your current shell, or in a sources profile like .bash-profile
...<pre>
export SPARK_HOME='/path/to/spark'
export PYSPARK_SUBMIT_ARGS='--master local[*]'
</pre>

5. Now you are ready to launch jupyter with your pyspark profile
...<pre>jupyter notebook --profile=pyspark</pre>

rather than configure jupyter locally with the above steps, you may also choose to run a jupyter notebook with pyspark kernel by using a [docker container](https://wegetsignal.wordpress.com/2016/03/23/jupyter-spark-docker/) 
_______

<h3>RStudio</h3>

Spark includes SparkR after version 1.4, including a REPL called sparkR. It can also be used within interactive notebook environments -- such as RStudio


<h4>You may need to install R</h4>
For Mac:
get the package here: https://cran.rstudio.com/bin/macosx/
or use a package manager like homebrew

For Windows:
?

<h4>Download RStudio Desktop Version</h4>
https://www.rstudio.com/products/rstudio/download/

----

<h2>DataBricks Community Edition</h2>

A free version of databricks spark platform for learning. 
There is a waiting list for accounts. 
No local installation needed! Awesome!

----

<h1>QUICK LINKS</h1>

Platforms:

* zeppelin http://zeppelin.incubator.apache.org/
* RStudio http://www.rstudio.com/products/rstudio/download/
* Jupyter http://jupyter.org/
* Databricks Community Edition: https://databricks.com/blog/2016/02/17/introducing-databricks-community-edition-apache-spark-for-all.html

Spark Further:
* Spark Programming Guide: http://spark.apache.org/docs/latest/programming-guide.html
* Get on mailing list and find out about conferences and events: http://spark.apache.org/community.html






