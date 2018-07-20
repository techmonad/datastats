version      := "1.0.0-SNAPSHOT"
scalaVersion := "2.12.6"
organization := "com.techmonad.datastats"

crossScalaVersions := Seq("2.11.12", "2.12.6")

resolvers += Resolver.sonatypeRepo("snapshots")

// -------------------------------------------------------------------------------------------------------------------
// Root Project
// -------------------------------------------------------------------------------------------------------------------
lazy val root = Project("datastats", file("."))
  .aggregate(common, repository, queryEngine, queueManager, dataEngine, restApi)
  .dependsOn(common, repository, queryEngine, queueManager, dataEngine, restApi)
  .settings(name := "datastats")  

// -------------------------------------------------------------------------------------------------------------------
// Datastats Common Module
// -------------------------------------------------------------------------------------------------------------------
lazy val common = Project("datastats-common", file("modules/datastats-common"))
  .settings(name := "datastats-common")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Repository Module
// -------------------------------------------------------------------------------------------------------------------
lazy val repository = Project("datastats-repository", file("modules/datastats-repository"))
  .aggregate(common).dependsOn(common)
  .settings(name := "datastats-reprository")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Query Engine
// -------------------------------------------------------------------------------------------------------------------
lazy val queryEngine = Project("datastats-query-engine", file("modules/datastats-query-engine"))
  .aggregate(common).dependsOn(common)
  .settings(name := "datastats-query-engine")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Queue Manager
// -------------------------------------------------------------------------------------------------------------------
lazy val queueManager = Project("datastats-queue-manager", file("modules/datastats-queue-manager"))
  .aggregate(common).dependsOn(common)
  .settings(name := "datastats-queue-manager")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Data Engine
// -------------------------------------------------------------------------------------------------------------------
lazy val dataEngine = Project("datastats-data-engine", file("modules/datastats-data-engine"))
  .aggregate(common, repository, queryEngine)
  .dependsOn(common, repository, queryEngine)
  .settings(name := "datastats-data-engine")
  .settings(libraryDependencies ++= Seq(scalaTest))
  
// -------------------------------------------------------------------------------------------------------------------
// Datastats REST API
// -------------------------------------------------------------------------------------------------------------------
lazy val restApi = Project("datastats-api", file("modules/datastats-api"))
  .aggregate(common, repository, queryEngine, queueManager)
  .dependsOn(common, repository, queryEngine, queueManager)
  .settings(name := "datastats-api")
  .settings(libraryDependencies ++= Seq(guice, h2Database, scalaTestPlus))
  .enablePlugins(PlayScala)

// Project Dependencies
val h2Database = "com.h2database" % "h2" % "1.4.196"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test
val scalaTestPlus = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

