/**
  * Created by msciab on 12/12/15.
  */
package object calories {

  // requests
  case class Login(username: String, password: String)

  case object Logout

  // responses
  case class LoggedUser(ticket: Either[String, Int],
                        role: String = "",
                        name: String = "",
                        username: String = "")

}

