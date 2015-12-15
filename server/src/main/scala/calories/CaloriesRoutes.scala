package calories

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.Config

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by msciab on 12/12/15.
  */
trait CaloriesRoutes extends UpickleSupport {
  //val config: Config
  //val logger: LoggingAdapter
  //implicit val system: ActorSystem
  //implicit def executor: ExecutionContextExecutor
  //implicit val materializer: ActorMaterializer

  def caloriesRoutes = path("/calories" / """\d\d\d\d-\d\d-\d\d""") {
    parameter('ticket.as[Long]) { ticket =>
      get {
        complete("get")
      } ~ post {
        complete("post")
      } ~ put {
        complete("put")
      } ~ delete {
        complete("delete")
      }

      /* post {
         formField('username.as[String], 'password.as[String]) {
           (username, password) =>
             complete {
               BoxOffice.issue(username, password)
             }
         }
       }*/
    }
  }
}
