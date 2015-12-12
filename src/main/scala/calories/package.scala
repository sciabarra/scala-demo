/**
  * Created by msciab on 12/12/15.
  */
package object calories {

  // requests
  case class Login(username: String, password: String)

  case object Logout

  // responses
  case class User(name: String, ticket: String, role: String)

}
