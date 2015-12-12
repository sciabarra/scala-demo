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
class LoginHandler[M](modelRW: ModelRW[M, Option[User]])
  extends ActionHandler(modelRW) {


  override def handle = {
    case Login(username, password) =>
      effectOnly(Effect(
        Ajax.get(s"${dom.location.origin}/login/${username}").map { r =>
          read[User](r.responseText)
        }))
    case Logout =>
      effectOnly(Effect(
        Ajax.get(s"${dom.location.origin}/logout").map { r =>
          read[User](r.responseText)
        }))
    case User("", _, _) =>
      updated(None)
    case user@User(name, role, ticket) =>
      updated(Some(user))
  }
}
