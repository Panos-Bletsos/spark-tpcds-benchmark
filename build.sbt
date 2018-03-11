
name := "spark-tpcds-benchmark"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.2",
  "com.databricks" %% "spark-sql-perf" % "0.5.0-SNAPSHOT",
  "org.apache.spark" %% "spark-core" % "2.2.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.2.0" % "provided"
)

lazy val app = (project in file("app"))
  .settings(mainClass in assembly := Some("bletsos.panos.App"))