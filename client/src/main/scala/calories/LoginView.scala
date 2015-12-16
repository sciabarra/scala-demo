package calories

import diode.{Dispatcher, ModelR}
import scalatags.JsDom.all._

/**
  * Created by msciab on 11/12/15.
  */
class LoginView(dispatch: Dispatcher) {

  def render = div(cls := "row", splash, login, sep, register)

  def sep = div(cls := "col-md-2")

  def splash = div(cls := "jumbotron",
    h1("Calories Counter"),
    p("On a diet? Register here, set your target and keep your daily dose of calories under control.")
  )

  def login = form(cls := "form col-md-5",
    h2("Login"),
    p("If you are already registered."),
    div(cls := "form-group",
      label("Login", `for` := "login"),
      input(id := "login", `type` := "text",
        cls := "form-control", autofocus := "", required := "")),

    div(cls := "form-group",
      label("Password", `for` := "password"),
      input(id := "password", `type` := "password",
        cls := "form-control", required := "")),

    button("Login", `type` := "submit",
      cls := "btn btn-lg btn-primary btn-block")
  )

  def register = form(cls := "form-signin col-md-5",
    h2("Register"),
    p("If you  dot not have yet an account."),

    div(cls := "form-group",
      input(id := "name", placeholder := "Full Name", `type` := "text",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "calories", placeholder := "Daily Calories Target", `type` := "number",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "login", placeholder := "Login", `type` := "text",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "password", placeholder := "Password", `type` := "text",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "password2", placeholder := "Repeat Password", `type` := "text",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      button("Register", `type` := "submit",
        cls := "btn btn-lg btn-primary btn-block")))

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

