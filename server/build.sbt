name         := "calories-server"
organization := "com.sciabarra"
version      := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"

enablePlugins(SbtWeb)

libraryDependencies ++= {
  val akkaV       = "2.3.12"
  val akkaStreamV = "2.0-M2"
  val scalaTestV  = "2.2.4"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11"                           % akkaV,
    "com.typesafe.akka" % "akka-stream-experimental_2.11"             % akkaStreamV,
    "com.typesafe.akka" % "akka-http-core-experimental_2.11"          % akkaStreamV,
    "com.typesafe.akka" % "akka-http-experimental_2.11"               % akkaStreamV,
    "com.typesafe.akka" % "akka-http-testkit-experimental_2.11"       % akkaStreamV,
    "org.scalatest"     %% "scalatest" % scalaTestV   % "test",
    "com.lihaoyi" %% "upickle" % "0.2.8",
    "com.lihaoyi" %% "autowire" % "0.2.5"
  )
}


scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

unmanagedSourceDirectories in Compile ++= Seq(
  baseDirectory.value.getParentFile /  "src" / "main" / "scala")


Revolver.settings

