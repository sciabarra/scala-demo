package calories

import calories._
import diode.{Effect, ModelRW, ActionHandler}
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import upickle._

/**
  * Created by msciab on 11/12/15.
  */
class LoginHandler[M](modelRW: ModelRW[M, LoggedUser])
  extends ActionHandler(modelRW)
  with Common {

  override def handle = {
    case Login(username, password) =>
      effectOnly(Effect(
        Ajax.post(s"${dom.location.origin}/login",
          s"username=${username}&password=${password}", 0, form)
          .map { r =>
            read[LoggedUser](r.responseText)
          }))

    case Logout(ticket) =>
      effectOnly(Effect(
        Ajax.get(s"${dom.location.origin}/logout/${ticket}")
          .map { r =>
            read[LoggedUser](r.responseText)
          }))

    case reg: Register =>
      effectOnly(Effect(
        Ajax.post(s"${dom.location.origin}/register",
          write(reg), 0, json).map { r =>
          read[LoggedUser](r.responseText)
        }))

    case Unregister(username) =>
      val ticket = modelRW.value.ticket.right.get
      effectOnly(Effect(
        Ajax.get(s"${dom.location.origin}/unregister/${ticket}/${username}")
          .map { r =>
            read[Users](r.responseText)
          }))

    case users: Users =>
      updated(modelRW.value.copy(data = Left(users.users)))

    case user: LoggedUser =>
      // if the user is not correct
      if (user.ticket.isLeft)
        updated(user)
      else {
        // set the user and request the current meals
        if (user.role.equals("admin"))
          updated(user, Effect(loadUsers(user.ticket.right.get)))
        else
          updated(user, Effect(MealHandler.loadMeals(user.ticket.right.get)))
      }
  }

  def loadUsers(ticket: Int) = {
    Ajax.get(s"${dom.location.origin}/users/${ticket}")
      .map { r =>
        read[Users](r.responseText)
      }
  }
}
