package calories

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Directives._
import akka.stream.FlowMaterializer
import com.typesafe.config.Config
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContextExecutor

/**
 * Created by msciab on 08/05/15.
 */
trait StaticService extends DefaultJsonProtocol {

  def config: Config

  val logger: LoggingAdapter

  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: FlowMaterializer


  def staticRoutes = pathSingleSlash {
    getFromFile(config.getString("files.home"))
  } ~ pathPrefix("app") {
    getFromBrowseableDirectory(config.getString("directories.app"))
  } ~ pathPrefix("lib") {
    getFromBrowseableDirectory(config.getString("directories.lib"))
  }

}
