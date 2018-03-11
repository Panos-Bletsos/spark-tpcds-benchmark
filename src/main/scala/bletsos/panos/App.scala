package bletsos.panos

import com.databricks.spark.sql.perf.tpcds.{TPCDS, TPCDSTables}

import com.typesafe.config.{Config, ConfigFactory}

import org.apache.spark.sql.SparkSession

object App {
  def main(args: Array[String]): Unit = {
    val conf: Config = ConfigFactory.load

    val spark: SparkSession = SparkSession
      .builder()
      .master(conf.getString("spark.master"))
      .appName("spark-tpcds-benchmark")
      .enableHiveSupport()
      .getOrCreate()

    // root directory of location to create data in
    val rootDir = conf.getString("dataGen.rootDir")
    // name of database to create.
    val databaseName = conf.getString("databaseName")
    // valid spark format like parquet "parquet".
    val format = conf.getString("dataGen.format")

    val tpcds = new TPCDS (sqlContext = spark.sqlContext)

    val resultLocation = conf.getString("resultLocation") // place to write results
    val iterations = conf.getInt("iterations") // how many iterations of queries to run.
    val queries = tpcds.tpcds2_4Queries // queries to run.
    val timeout = 24 * 60 * 60 // timeout, in seconds.

    spark.sql(s"use $databaseName")
    val experiment = tpcds.runExperiment(
      queries,
      iterations = iterations,
      resultLocation = resultLocation,
      forkThread = conf.getBoolean("forkThread"))
    experiment.waitForFinish(timeout)
  }
}