package calories

import diode.{Dispatcher, ModelR}
import org.scalajs.jquery.{jQuery => $}

import scalatags.JsDom.all._

/**
  * Created by msciab on 16/12/15.
  */
class UserView(users: ModelR[Array[Register]], dispatch: Dispatcher) {

  def render = div(cls := "row",
    div(cls := "col-md-3"),
    div(cls := "col-md-6", userTable, logout),
    div(cls := "col-md-3")
  )

  def logout = button("Logout", `type` := "button",
    cls := "btn btn-lg btn-primary btn-block",
    onclick := { () => dispatch(Logout(0)) }
  )

  def userTable = div(cls := "panel panel-default",
    table(cls := "table table-bordered",
      thead(tr(
        th("Login"),
        th("Name"),
        th("Calories"),
        th("Actions")
      )), tbody(
        for (user <- users.value)
          yield tr(
            td(width := "30%", i(s"${user.name}")),
            td(width := "25%", s"${user.username}"),
            td(width := "15%", b(s"${user.calories}")),
            td(width := "15%",
              input(tpe := "button", value := "Delete",
                onclick := { () => dispatch(Unregister(user.username)) })),
            td(width := "15%",
              input(tpe := "button", value := "Log As",
                onclick := { () => dispatch(Login(user.username, user.password)) })
            )))))
}

