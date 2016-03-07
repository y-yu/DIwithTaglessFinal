name := "DIwithTaglessFinal"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ws" % "2.5.0"
)

mainClass := Some("Main")

scalacOptions += "-language:higherKinds"
