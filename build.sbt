name := """arithmetic-calculator"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "6.0.0-RC2" % Test
libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.2"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.13"
libraryDependencies += "io.jsonwebtoken" % "jjwt-api" % "0.11.2"
libraryDependencies += "io.jsonwebtoken" % "jjwt-impl" % "0.11.2"
libraryDependencies += "io.jsonwebtoken" % "jjwt-jackson" % "0.11.2"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"
libraryDependencies ++= Seq(guice, ws, specs2 % Test)
libraryDependencies += filters
libraryDependencies += jdbc % Test
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "x.x.x" % "test"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
