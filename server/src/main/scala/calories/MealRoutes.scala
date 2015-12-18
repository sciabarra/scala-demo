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

  val mealRoutes = {
    path("meal" / IntNumber) {
      ticket =>
        get {
          logger.debug(s"GET meals")
          complete(Meals(MealDao.load(ticket)))
        } ~ post {
          entity(as[Meal]) {
            meal =>
              logger.debug(s"POST meal ${meal}")
              complete(Meals(MealDao.add(ticket, meal)))
          }
        }
    } ~ path("meal" / IntNumber / Segment) { (ticket, id) =>
      delete {
        complete(Meals(MealDao.remove(ticket, id)))
      }
    }
  }
}
