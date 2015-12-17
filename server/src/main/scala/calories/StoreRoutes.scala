package calories

import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging
import upickle._

/**
  * Created by msciab on 17/12/15.
  */
trait StoreRoutes
  extends LazyLogging
  with UpickleSupport {

  val storeRoutes = path("store") {
    logger.debug("store!")
    get {
      complete("store: get")
    } ~ post {
      entity(as[Meal]) {
        s =>
          logger.debug(s"store: post ${s}")
          //val meal = read[Meal](s)
          //complete(Array[Meal](meal, meal))
          complete(s)
      }
      //complete("salve!!!")
    } ~ put {
      complete("put")
    } ~ delete {
      complete("delete")
    }
  }
}


