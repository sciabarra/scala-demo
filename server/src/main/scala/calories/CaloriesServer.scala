package calories

import akka.actor.ActorSystem
import akka.event.Logging
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
  with LoginRoutes {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher
  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  //println(config.getString("files.home"))
  //println(config.getString("directories.app"))

  Http().bindAndHandle(
    staticRoutes ~ loginRoutes,
    config.getString("http.interface"),
    config.getInt("http.port"))
}
