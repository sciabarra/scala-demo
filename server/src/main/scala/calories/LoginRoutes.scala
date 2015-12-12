package calories

import java.util.Random

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.server.Directives._
import upickle._

/**
  * Created by msciab on 12/12/15.
  */
trait LoginRoutes {
  val config: Config
  val logger: LoggingAdapter
  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: ActorMaterializer

  val rnd = new Random()

  def loginRoutes = pathPrefix("login") {
    get {
      complete {
        val ticket = rnd.nextLong().toString
        write(User("User", "user", ticket))
      }
    }
  } ~ pathPrefix("logout") {
    get {
      complete {
        write(User("","",""))
      }
    }
  }
}
