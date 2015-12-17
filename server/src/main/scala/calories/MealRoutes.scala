package calories

import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging
import upickle._

/**
  * Created by msciab on 17/12/15.
  */
trait MealRoutes
  extends LazyLogging
  with UpickleSupport {

  val mealRoutes = path("meal" / IntNumber) {
    ticket =>
      get {
        logger.debug(s"GET meals")
        complete(MealDao.load(ticket))
      } ~ post {
        entity(as[Meal]) {
          meal =>
            logger.debug(s"POST meal ${meal}")
            complete(MealDao.add(ticket, meal))
        }
      } ~ delete {
        entity(as[Int]) {
          index => complete(MealDao.remove(ticket, index))
        }
      }
  }
}


