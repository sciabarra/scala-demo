package calories

import diode.{ModelRW, ActionHandler}

/**
  * Created by msciab on 11/12/15.
  */

case class Login(username: String, password: String)

case object Logout

class LoginHandler[M](modelRW: ModelRW[M, Option[String]])
  extends ActionHandler(modelRW) {
  override def handle = {
    case Login(user: String, pass: String) =>
      // TODO ajax call
      updated(Some(user))
    case Logout =>
      // TODO ajax call
      updated(None)
  }
}
