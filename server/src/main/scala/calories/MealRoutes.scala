package calories

import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging
import upickle._
import org.joda.time.{LocalTime, LocalDate, DateTime}

/**
  * Created by msciab on 17/12/15.
  */
trait MealRoutes
  extends LazyLogging
  with UpickleSupport {

  val dateRe = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
  val timeRe = """(\d\d):(\d\d)""".r

  val mealRoutes = {
    path("meal" / IntNumber) {
      (ticket) =>
        get {
          parameters('fromDate ? "",  'toDate ? "", 'fromTime ? "", 'toTime ? "") {
            (fromDateS, toDateS, fromTimeS, toTimeS) =>
              val fromDate = fromDateS match {
                case dateRe(y, m, d) => new LocalDate(y.toInt, m.toInt, d.toInt)
                case _ => new LocalDate(0, 1, 1) // beginning of time
              }
              val toDate = toDateS match {
                case dateRe(y, m, d) => new LocalDate(y.toInt, m.toInt, d.toInt)
                case _ => new LocalDate(3000, 1, 1) // end of time
                // yes, we have a year 3000 bug here :)
              }
              val fromTime = fromTimeS match {
                case timeRe(h, m) => new LocalTime(h.toInt, m.toInt, 0)
                case _ => new LocalTime(0, 0, 0) // start of time
              }
              val toTime = toTimeS match {
                case timeRe(h, m) => new LocalTime(h.toInt, m.toInt, 0)
                case _ => new LocalTime(23, 59, 59) // end of time
              }
              logger.debug(s"${fromDate}/${toDate} ${fromTime}/${toTime}")
              complete(Meals(MealDao.loadFiltered(ticket,
                fromDate, toDate, fromTime, toTime)))
              //complete(Meals(MealDao.load(ticket)))
          }
        }
    } ~ path("meal" / IntNumber) {
      ticket =>
        post {
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