package calories

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.server.Directives._

/**
 * Serve http requests for static files
 */
trait StaticRoutes extends LazyLogging {

  val config: Config

  val staticRoutes = pathSingleSlash {
    logger.debug("static routes")
    getFromFile(config.getString("files.home"))
  } ~ pathPrefix("app") {
    getFromBrowseableDirectory(config.getString("directories.app"))
  } ~ pathPrefix("lib") {
    getFromBrowseableDirectory(config.getString("directories.lib"))
  }
}
