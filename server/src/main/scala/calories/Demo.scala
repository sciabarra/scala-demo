package calories

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

/**
  * Created by msciab on 17/12/15.
  */
object Demo
  extends UpickleSupport {
  // with App
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher

  Http().bindAndHandle(
    path("hello") {
      post {
        entity(as[Login]) { l =>
          val m = Login(l.password, l.username)
          //complete(m)
          //val n = BoxOffice.issue(l.username, l.password)
          val n = LoggedUser(Left("bad"))
          complete(m)
        }
      }
    },

    "0.0.0.0",
    9999)
}
