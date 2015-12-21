Installation from Sources
=========================

1. Install SBT from here:

 http://www.scala-sbt.org

2. Build

Use this command

 sbt s/assets s/compile c/fastOptJS

3. Run

cd server
sbt run

then open the browser as http://localhost:9000

4. Execute Functional Tests on REST API:

(they must be while the server is running in localhost!)

open another terminal
cd in the main folder
sbt s/test

KNOWN ISSUES

- occasionally one test fails the first time it is run
to be investigated

- the server after a few minutes throws an exception
it is a documented issues in akka-http-M2

