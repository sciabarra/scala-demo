package calories

import diode.{ActionHandler, Effect, ModelRW}
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import upickle._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import org.scalajs.jquery.{jQuery => $}

import scala.scalajs.js

/**
  * Created by msciab on 11/12/15.
  */
class MealHandler[M](modelRW: ModelRW[M, LoggedUser])
  extends ActionHandler(modelRW) {

  override def handle = {

    case meal: Meal =>
      val ticket = modelRW.value.ticket.right.get
      effectOnly(Effect(MealHandler.saveMeal(ticket, meal)))

    case meals: Meals =>
      updated(modelRW.value.copy(data = Right(meals.meals)))

    case MealDelete(id) =>
      val ticket = modelRW.value.ticket.right.get
      effectOnly(Effect(MealHandler.deleteMeal(ticket, id)))

    case MealFilter =>
      val ticket = modelRW.value.ticket.right.get
      effectOnly(Effect(MealHandler.loadMeals(ticket)))
  }
}

object MealHandler extends Common {

  def filter = {
    val df1 = $("#datefilter1").`val`()
    val r = if (js.isUndefined(df1))
      s"fromDate=${today}"
    else {
      val df2 = $("#datefilter2").`val`()
      val tf1 = $("#timefilter1").`val`()
      val tf2 = $("#timefilter2").`val`()
      s"fromDate=${df1}&toDate=${df2}&fromTime=${tf1}&toTime=${tf2}"
    }
    println(r)
    r
  }

  def loadMeals(ticket: Int) = Ajax.get(
    s"${dom.location.origin}/meal/${ticket}?${filter}")
    .map { r =>
      read[Meals](r.responseText)
    }

  def saveMeal(ticket: Int, meal: Meal) = Ajax.post(
    s"${dom.location.origin}/meal/${ticket}?${filter}", write(meal), 0, json)
    .map { r =>
      read[Meals](r.responseText)
    }

  def deleteMeal(ticket: Int, id: String) = Ajax.delete(
    s"${dom.location.origin}/meal/${ticket}/${id}?${filter}")
    .map { r =>
      read[Meals](r.responseText)
    }
}