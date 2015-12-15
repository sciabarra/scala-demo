package calories

import diode.{Dispatcher, ModelR}
import scalatags.JsDom.all._

/**
  * Created by msciab on 11/12/15.
  */
class LoginView(userOpt: ModelR[Option[LoggedUser]], dispatch: Dispatcher) {
  def render = if (userOpt().isEmpty) {
    div(cls := "row",
      div(cls := "col-md-4"),
      form(cls := "form-signin col-md-4",
        h2("Please sign in", cls := "form-signin-heading"),
        label(cls := "sr-only", `for` := "inputEmail", "Email address"),
        input(id := "inputEmail", cls := "form-control", `type` := "email", autofocus := "", required := "", placeholder := "Email address"),
        label(cls := "sr-only", `for` := "inputPassword", "Password"),
        input(id := "inputPassword", cls := "form-control", `type` := "password", required := " ", placeholder := "Password"),
        button(cls := "btn btn-lg btn-primary btn-block", `type` := "submit", "Sign in")
      ),
      div(cls := "col-md-4"))
  } else {
    val user = userOpt().get
    p(s"Welcome ${user.name}, ${user.role} (${user.ticket})")
  }

  /*
  div(cls := "btn-group",
    if (userOpt().isEmpty)
      button("Login", cls := "btn btn-default", onclick := { () => dispatch(Login("username", "password")) }
      )
    else
      button("Logout", cls := "btn btn-default", onclick := { () => dispatch(Logout) }
      ))
  )*/
}

