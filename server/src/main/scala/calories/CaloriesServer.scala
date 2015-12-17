package calories

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.server.Directives._

/**
  * The backend server, handle http requests
  */
object CaloriesServer
  extends App
  with StaticRoutes
  with LoginRoutes
  with MealRoutes
  with UpickleSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher
  val config = ConfigFactory.load()

  Http().bindAndHandle(
    staticRoutes ~ mealRoutes ~ loginRoutes,
    config.getString("http.interface"),
    config.getInt("http.port"))
}
