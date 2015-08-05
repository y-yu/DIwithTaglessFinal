name := "DIwithTaglessFinal"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ws" % "2.4.0-M2"
)

mainClass := Some("Main")

scalacOptions += "-language:higherKinds"