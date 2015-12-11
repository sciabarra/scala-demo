name         := "calories-server"
organization := "com.sciabarra"
version      := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"

enablePlugins(SbtWeb)

libraryDependencies ++= {
  val akkaV       = "2.3.10"
  val akkaStreamV = "1.0-RC2"
  val scalaTestV  = "2.2.4"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-scala-experimental"         % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-scala-experimental" % akkaStreamV,
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test",
    "com.lihaoyi" %% "autowire" % "0.2.5",
    "com.lihaoyi" %% "upickle" % "0.2.8"
  )
}


scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

unmanagedSourceDirectories in Compile ++= Seq(
  baseDirectory.value.getParentFile /  "src" / "main" / "scala")


Revolver.settings

