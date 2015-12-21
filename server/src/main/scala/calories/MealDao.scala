package calories

import java.io.{FileWriter, File}

import com.typesafe.scalalogging.LazyLogging
import akka.http.scaladsl.model.{DateTime => _}
import org.joda.time.{DateTime, LocalDate, LocalTime}
import scala.io.Source
import upickle._

/**
  * Dao to read/write meals in a file (as serialized json)
  */
object MealDao extends LazyLogging {

  case class Filter(fromDate: LocalDate, toDate: LocalDate, fromTime: LocalTime, toTime: LocalTime)

  // utils
  var currId: Long = System.currentTimeMillis

  def file(dir: File, file: String) = new File(dir, file)

  def file(dir: String) = new File(dir)

  def fileOf(ticket: Int) = {
    val user = BoxOffice.getUser(ticket)
    if (user.ticket.isRight) {
      Some(file(file(file("data"), user.username), "data.json"))
    } else None
  }

  /**
    * Load all data by ticket
    *
    * @param ticket
    * @return
    */
  def load(ticket: Int): Array[Meal] = {
    val dataFile = fileOf(ticket)
    if (dataFile.nonEmpty && dataFile.get.exists) {
      read[Array[Meal]](Source.fromFile(dataFile.get).mkString)
    } else {
      Array.empty[Meal]
    }
  }

  def checkLimits(meals: Array[Meal], limit: Int): Map[String,Boolean] = {

    def sumCalories(meals: Array[Meal]) = meals.map(_.calories).sum

    meals.groupBy(_.date) // date -> List(meal)
      .map(x => x._1 -> (sumCalories(x._2) > limit)) // checkCalories over limit

  }

  /**
    *
    * Load all data , filter by date/time, add the color
    *
    * @param ticket
    * @param filter
    * @return
    */
  def loadFiltered(ticket: Int, filter: Filter): Array[Meal] = {
    // load all the meals
    val all = load(ticket)
    val overLimitMap = checkLimits(all, BoxOffice.getUser(ticket).calories)

    logger.debug("limitMap=${overLimitMap}")


    val filteredList = all.map { meal =>
      val dt = new DateTime(s"${meal.date}T${meal.time}")
      //println(s"${dt}->${meal}")
      dt -> meal
    }. // array of meals
      sortWith(_._1.toInstant.getMillis < _._1.toInstant.getMillis). // array of (dateTimeOfMeal, meal)
      filter { dtMeal =>
      val ld = new LocalDate(dtMeal._1) // get a date without time
      (ld.isEqual(filter.fromDate) || ld.isAfter(filter.fromDate)) &&
        (ld.isBefore(filter.toDate))
    }.filter { dtMeal =>
      val lt = new LocalTime(dtMeal._1) // get a time without date
      (lt.isEqual(filter.fromTime) || lt.isAfter(filter.fromTime)) &&
        (lt.isEqual(filter.toTime) || lt.isBefore(filter.toTime))
    }.map(_._2) // extra meals again

     // .map(_.copy(_.isOver))
    filteredList.map(x => x.copy(isOver=overLimitMap(x.date)))
  }


  /**
    * Save data by ticket
    *
    * @param ticket
    * @param meals
    * @return
    */
  def save(ticket: Int, meals: Array[Meal]): Array[Meal] = {
    val dataFile = fileOf(ticket)
    if (dataFile.isEmpty)
      Array.empty[Meal]
    else {
      try {
        val fw = new FileWriter(dataFile.get)
        val json = write(meals)
        fw.write(json)
        fw.close
        meals
      } catch {
        case _: Throwable => Array.empty[Meal]
      }
    }
  }

  /**
    * Add a meal to the meals of an user
    *
    * @param ticket
    * @param meal
    * @return
    */
  def add(ticket: Int, meal: Meal, filter: Filter): Array[Meal] = {
    val meals = load(ticket)
    currId = currId + 1
    save(ticket, meals :+ (meal.copy(id = currId.toHexString)))
    loadFiltered(ticket, filter)
  }

  /**
    * Remove the meal by index
    *
    * @param ticket
    * @param id
    * @return
    */
  def remove(ticket: Int, id: String, filter: Filter): Array[Meal] = {
    val meals = load(ticket)
    val newMeals = meals.filter(_.id != id)
    save(ticket, newMeals)
    loadFiltered(ticket, filter)
  }

}
