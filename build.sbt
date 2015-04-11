name := """olivier-bruchez-name"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies ++= Seq(
  "com.github.rjeschke" % "txtmark" % "0.13",
  "joda-time" % "joda-time" % "2.7",
  "org.twitter4j" % "twitter4j-core" % "4.0.3")
