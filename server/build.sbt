name         := "calories-server"
organization := "com.sciabarra"
version      := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"

resolvers += Resolver.sonatypeRepo("public")

val akkaV       = "2.3.12"
val akkaStreamV = "2.0-M2"
val scalaTestV  = "2.2.4"

libraryDependencies ++= Seq("junit" % "junit" % "4.11" % "test"
     , "ch.qos.logback"         % "logback-classic" % "1.1.3"
     , "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
     , "com.novocode"           % "junit-interface" % "0.11" % "test"
     , "com.typesafe.akka"      % "akka-actor_2.11"                        % akkaV
     , "com.typesafe.akka"      % "akka-stream-experimental_2.11"          % akkaStreamV
     , "com.typesafe.akka"      % "akka-http-core-experimental_2.11"       % akkaStreamV
     , "com.typesafe.akka"      % "akka-http-experimental_2.11"            % akkaStreamV
     , "com.typesafe.akka"      % "akka-http-testkit-experimental_2.11"    % akkaStreamV
     , "com.jayway.restassured" % "rest-assured" % "2.8.0" % "test"
     , "com.jayway.restassured" % "json-path" % "2.8.0" % "test"
     , "com.jayway.restassured" % "json-schema-validator" % "2.8.0" % "test"
     , "com.lihaoyi"            %% "upickle" % "0.2.8"
     , "org.webjars.bower"      % "bootstrap" % "3.0.0"
     , "org.webjars"            % "bootstrap-datepicker" % "1.4.0"
     , "org.webjars"            % "bootstrap-timepicker" % "0.2.3-1"
     )

enablePlugins(SbtWeb)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

// support shared with and without symbolic - symlink needed for intellij
unmanagedSourceDirectories in Compile ++= (if( (baseDirectory.value / "shared" / "src").isDirectory)
  Seq(baseDirectory.value / "shared" /  "src" / "main" / "scala")
  else Seq( baseDirectory.value.getParentFile / "shared" /  "src" / "main" / "scala" ))

Revolver.settings

