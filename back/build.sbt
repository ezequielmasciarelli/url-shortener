name := """urlshortener"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test


// https://mvnrepository.com/artifact/com.typesafe.slick/slick
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.3"

// https://mvnrepository.com/artifact/org.slf4j/slf4j-nop
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.30" % Test

// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.21"

// https://mvnrepository.com/artifact/com.typesafe.slick/slick-hikaricp
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"


val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)