name := "calories-client"
organization := "com.sciabarra"
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "com.lihaoyi" %%% "scalatags" % "0.5.3",
  "me.chrons" %%% "diode" % "0.2.0",
  "com.lihaoyi" %%% "upickle" % "0.2.8",
  "com.lihaoyi" %%% "utest" % "0.3.1" % "test"
)

enablePlugins(ScalaJSPlugin)

//workbenchSettings

// support shared with and without symbolic - symlink needed for intellij
unmanagedSourceDirectories in Compile ++= (if( (baseDirectory.value / "shared" / "src").isDirectory)
  Seq(baseDirectory.value / "shared" /  "src" / "main" / "scala")
else Seq( baseDirectory.value.getParentFile / "shared" /  "src" / "main" / "scala" ))

testFrameworks += new TestFramework("utest.runner.Framework")

bootSnippet := "CaloriesApp().main();"
