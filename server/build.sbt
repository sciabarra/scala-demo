name         := "calories-server"
organization := "com.sciabarra"
version      := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"

resolvers += Resolver.sonatypeRepo("public")

val akkaV       = "2.3.12"
val akkaStreamV = "2.0-M2"
val scalaTestV  = "2.2.4"

libraryDependencies ++= Seq("junit" % "junit" % "4.11" % "test"
     , "com.novocode"           % "junit-interface" % "0.11" % "test"
     , "com.typesafe.akka"      % "akka-actor_2.11"                        % akkaV
     , "com.typesafe.akka"      % "akka-stream-experimental_2.11"          % akkaStreamV
     , "com.typesafe.akka"      % "akka-http-core-experimental_2.11"       % akkaStreamV
     , "com.typesafe.akka"      % "akka-http-experimental_2.11"            % akkaStreamV
     , "com.typesafe.akka"      % "akka-http-testkit-experimental_2.11"    % akkaStreamV
     , "com.jayway.restassured" % "rest-assured" % "2.4.1" % "test"
     , "com.jayway.restassured" % "json-path" % "2.4.1" % "test"
     , "com.jayway.restassured" % "json-schema-validator" % "2.4.1" % "test"
     , "com.lihaoyi"            %% "upickle" % "0.2.8"
     //,"org.scalatest"     %% "scalatest" % scalaTestV   % "test"
     )


enablePlugins(SbtWeb)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

// support shared with and without symbolic - symlink needed for intellij
unmanagedSourceDirectories in Compile ++= (if( (baseDirectory.value / "shared" / "src").isDirectory)
  Seq(baseDirectory.value / "shared" /  "src" / "main" / "scala")
  else Seq( baseDirectory.value.getParentFile / "shared" /  "src" / "main" / "scala" ))

Revolver.settings

