Instructions

1. Install SBT from here:

 http://www.scala-sbt.org

2. Build

Use this command

 sbt s/assets s/compile c/fastOptJS

3. Run

cd server
sbt run

then open the browser as http://localhost:9000

4. Execute Functional (REST API) Tests:

(while the server is running!)

open another terminal
cd in the main 
sbt s/test

KNOWN ISSUE: on windows it does not delete the user folder (probably a locking issue)


