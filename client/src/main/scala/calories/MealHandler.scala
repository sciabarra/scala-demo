package calories

import diode.{ActionHandler, Effect, ModelRW}
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import upickle._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

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
  }
}

object MealHandler {
  val json = Map("Content-Type" -> "application/json")

  def loadMeals(ticket: Int) = Ajax.get(
    s"${dom.location.origin}/meal/${ticket}")
    .map { r =>
      read[Meals](r.responseText)
    }

  def saveMeal(ticket: Int, meal: Meal) = Ajax.post(
    s"${dom.location.origin}/meal/${ticket}", write(meal), 0, json)
    .map { r =>
      read[Meals](r.responseText)
    }

  def deleteMeal(ticket: Int, id: String) = Ajax.delete(
    s"${dom.location.origin}/meal/${ticket}/${id}")
    .map { r =>
      read[Meals](r.responseText)
    }

}