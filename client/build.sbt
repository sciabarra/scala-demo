name         := "calories-client"
organization := "com.sciabarra"
version      := "1.0-SNAPSHOT"
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "com.lihaoyi" %%% "scalatags" % "0.5.3",
  "me.chrons" %%% "diode" % "0.2.0",
  "com.lihaoyi" %%% "utest" % "0.3.1" % "test"
)

enablePlugins(ScalaJSPlugin)

workbenchSettings

testFrameworks += new TestFramework("utest.runner.Framework")

bootSnippet := "CaloriesApp().main();"


