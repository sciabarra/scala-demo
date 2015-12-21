Instructions

1. Install SBT from here:

 http://www.scala-sbt.org

2. Build

Use this command

 sbt s/assets s/compile c/fastOptJS

3. Run

 sbt s/run

then open the browser as http://localhost:9000

4. Execute Functional (REST API) Tests:

(while the server is running!)

sbt s/test

