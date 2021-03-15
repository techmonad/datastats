version      := "1.0.0-SNAPSHOT"
scalaVersion := "2.13.5"
organization := "com.techmonad.datastats"

crossScalaVersions := Seq("2.11.12", "2.13.5")

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
// Datastats Utility Module
// -------------------------------------------------------------------------------------------------------------------
lazy val utility = Project("datastats-utility", file("modules/datastats-utility"))
  .aggregate(common).dependsOn(common)
  .settings(name := "datastats-utility")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Repository Module
// -------------------------------------------------------------------------------------------------------------------
lazy val repository = Project("datastats-repository", file("modules/datastats-repository"))
  .aggregate(common, utility).dependsOn(common, utility)
  .settings(name := "datastats-reprository")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Query Engine
// -------------------------------------------------------------------------------------------------------------------
lazy val queryEngine = Project("datastats-query-engine", file("modules/datastats-query-engine"))
  .aggregate(common, utility).dependsOn(common, utility)
  .settings(name := "datastats-query-engine")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Queue Manager
// -------------------------------------------------------------------------------------------------------------------
lazy val queueManager = Project("datastats-queue-manager", file("modules/datastats-queue-manager"))
  .aggregate(common, utility).dependsOn(common, utility)
  .settings(name := "datastats-queue-manager")
  .settings(libraryDependencies ++= Seq(scalaTest))

// -------------------------------------------------------------------------------------------------------------------
// Datastats Data Engine
// -------------------------------------------------------------------------------------------------------------------
lazy val dataEngine = Project("datastats-data-engine", file("modules/datastats-data-engine"))
  .aggregate(common, utility, repository, queryEngine)
  .dependsOn(common, utility, repository, queryEngine)
  .settings(name := "datastats-data-engine")
  .settings(libraryDependencies ++= Seq(scalaTest))
  
// -------------------------------------------------------------------------------------------------------------------
// Datastats REST API
// -------------------------------------------------------------------------------------------------------------------
lazy val restApi = Project("datastats-api", file("modules/datastats-api"))
  .aggregate(common, utility, repository, queryEngine, queueManager)
  .dependsOn(common, utility, repository, queryEngine, queueManager)
  .settings(name := "datastats-api")
  .settings(libraryDependencies ++= Seq(guice, h2Database, scalaTestPlus))
  .enablePlugins(PlayScala)

// Project Dependencies
val h2Database = "com.h2database" % "h2" % "1.4.200"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.6" % Test
val scalaTestPlus = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

