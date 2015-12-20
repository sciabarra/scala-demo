package calories

import java.io.{FileWriter, File}

import com.typesafe.scalalogging.LazyLogging
import akka.http.scaladsl.model.{DateTime => _}

import scala.io.Source

import upickle._

/**
  * Dao to read/write meals in a file (as serialized json)
  */
object MealDao extends LazyLogging {

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

  import org.joda.time.{DateTime, LocalDate, LocalTime}

  def loadFiltered(ticket: Int,
                   fromDate: LocalDate, toDate: LocalDate,
                   fromTime: LocalTime, toTime: LocalTime): Array[Meal] =
    load(ticket).
      map { meal =>
        val dt = new DateTime(s"${meal.date}T${meal.time}")
        //println(s"${dt}->${meal}")
        dt -> meal
      }. // array of meals
      sortWith(_._1.toInstant.getMillis < _._1.toInstant.getMillis). // array of (dateTimeOfMeal, meal)
      filter { dtMeal =>
      val ld = new LocalDate(dtMeal._1) // get a date without time
      (ld.isEqual(fromDate) || ld.isAfter(fromDate)) &&
        (ld.isBefore(toDate))
    }.filter { dtMeal =>
      val lt = new LocalTime(dtMeal._1) // get a time without date
      (lt.isEqual(fromTime) || lt.isAfter(fromTime)) &&
        (lt.isEqual(toTime) || lt.isBefore(toTime))
    }.
      map(_._2)

  // map of meals


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
  def add(ticket: Int, meal: Meal): Array[Meal] = {
    val meals = load(ticket)
    currId = currId + 1
    save(ticket, meals :+ (meal.copy(id = currId.toHexString)))
  }

  /**
    * Remove the meal by index
    *
    * @param ticket
    * @param id
    * @return
    */
  def remove(ticket: Int, id: String): Array[Meal] = {
    val meals = load(ticket)
    val newMeals = meals.filter(_.id != id)
    save(ticket, newMeals)
  }

}
