package calories

import java.util.Random

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.{ContentTypes, HttpResponse}
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.{Unmarshaller, FromResponseUnmarshaller}
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import scala.concurrent.{Future, ExecutionContext, ExecutionContextExecutor}
import akka.http.scaladsl.server.Directives._
import upickle._

/**
  * Created by msciab on 12/12/15.
  */
trait LoginRoutes extends UpickleSupport {
  val config: Config
  val logger: LoggingAdapter
  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor
  implicit val materializer: ActorMaterializer

  def loginRoutes = path("login") {
    post {
      formField('username.as[String], 'password.as[String]) {
        (username, password) =>
          complete {
            BoxOffice.issue(username, password)
          }
      }
    }
  } ~ path("logout" / Segment) {
    user =>
      complete {
        BoxOffice.invalidate(user)
      }
  } ~ path("register") {
    post {
      formField('name.as[String], 'username.as[String], 'password.as[String]) {
        (name, username, password) =>
          complete {
            BoxOffice.register(name, username, password)
          }
      }
    }
  } ~ path("cleanup" / Segment ) {
    user =>
      complete {
        BoxOffice.cleanup(user)
      }
  }
}
