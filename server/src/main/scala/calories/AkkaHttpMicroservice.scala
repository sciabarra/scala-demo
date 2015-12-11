package calories

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorFlowMaterializer
import com.typesafe.config.ConfigFactory


object AkkaHttpMicroservice
  extends App
  with StaticService
 {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher

  override implicit val materializer = ActorFlowMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  println(config.getString("files.home"))
  println(config.getString("directories.app"))
  Http().bindAndHandle(
    staticRoutes,
    config.getString("http.interface"),
    config.getInt("http.port"))
}
