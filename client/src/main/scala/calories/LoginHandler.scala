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
  extends ActionHandler(modelRW) {

  val fm = Map("Content-Type" -> "application/x-www-form-urlencoded")

  override def handle = {
    case Login(username, password) =>
      effectOnly(Effect(
        Ajax.post(s"${dom.location.origin}/login",
          s"username=${username}&password=${password}", 0,fm).map { r =>
          read[LoggedUser](r.responseText)
        }))
    case Logout(ticket) =>
      effectOnly(Effect(
        Ajax.get(s"${dom.location.origin}/logout/${ticket}").map { r =>
          read[LoggedUser](r.responseText)
        }))
    case user@LoggedUser(_, _, _, _) =>
      updated(user)
  }
}
