package calories

import diode.{Dispatcher, ModelR}
import scalatags.JsDom.all._
import org.scalajs.jquery.{jQuery => $}

/**
  * Created by msciab on 11/12/15.
  */
class LoginView(message: ModelR[String], dispatch: Dispatcher) {

  def render = div(cls := "row", splash, note, login, sep, register)

  def sep = div(cls := "col-md-2")

  def splash = div(cls := "jumbotron",
    h1("Calories Counter"),
    h2(
      """
      On a diet?
      Register here,set your daily calories target
      and keep your daily dose of calories under control."""))

  def note = {
    val msg = message.value
    div(cls := "col-md-12",
      if (msg.endsWith("!"))
        div(cls := "alert alert-danger", msg)
      else
        div(cls := "alert alert-info", msg))
  }

  def login = form(cls := "form col-md-6",
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

    button("Login", `type` := "button",
      cls := "btn btn-lg btn-primary btn-block",
      onclick := { () =>
        val l = Login(
          $("#login").value().toString,
          $("#password").value().toString)
        println(l)
        dispatch(l)
      }
    )
  )

  def register = form(cls := "form-signin col-md-6",
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
      button("Register", `type` := "button",
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

