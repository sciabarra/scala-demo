package calories

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.server.Directives._

/**
 * Created by msciab on 08/05/15.
 */
trait StaticRoutes {

  val config: Config
  val logger: LoggingAdapter
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: ActorMaterializer

  def staticRoutes = pathSingleSlash {
    getFromFile(config.getString("files.home"))
  } ~ pathPrefix("app") {
    getFromBrowseableDirectory(config.getString("directories.app"))
  } ~ pathPrefix("lib") {
    getFromBrowseableDirectory(config.getString("directories.lib"))
  }

}
