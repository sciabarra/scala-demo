package calories

import diode.{Dispatcher, ModelR}
import org.scalajs.dom
import scala.util.Try
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
    h3(
      """On a diet? Register here,set your daily calories target
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
      input(id := "login", `type` := "text", value := "bob",
        cls := "form-control", autofocus := "", required := "")),

    div(cls := "form-group",
      label("Password", `for` := "password"),
      input(id := "password", `type` := "password", value := "password",
        cls := "form-control", required := "")),

    button("Login", `type` := "button",
      cls := "btn btn-lg btn-primary btn-block",
      onclick := { () =>
        val l = Login(
          $("#login").value().toString,
          $("#password").value().toString)
        dispatch(l)
      }
    )
  )

  def register = form(cls := "form-signin col-md-6",
    h2("Register"),
    p("If you  dot not have yet an account."),

    div(cls := "form-group",
      input(id := "calories", placeholder := "Daily Calories Target", `type` := "number",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "name", placeholder := "Full Name", `type` := "text",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "username", placeholder := "Login", `type` := "text",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "password1", placeholder := "Password", `type` := "password",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      input(id := "password2", placeholder := "Repeat Password", `type` := "password",
        cls := "form-control", required := "")),
    div(cls := "form-group",
      button("Register", `type` := "button",
        cls := "btn btn-lg btn-primary btn-block",
        onclick := { () =>
          val password1 = $("#password1").value().toString
          val password2 = $("#password2").value().toString
          val name = $("#name").value().toString
          val username = $("#username").value().toString

          val calories = Try(Integer.parseInt($("#calories").value().toString))

          if (!password1.equals(password2)) {
            dom.alert("Passwords don't match!")
          } else if (calories.isFailure || calories.get < 100 || calories.get > 10000) {
            dom.alert("Please, provide a reasonable number of calories!")
          } else if (name.trim.size == 0 || username.trim.size == 0 || password1.size == 0) {
            dom.alert("Please fill all the fields!")
          } else {
            dispatch(Register(name = name,
              username = username,
              calories = calories.get,
              password = password1))
          }
        })))
}

