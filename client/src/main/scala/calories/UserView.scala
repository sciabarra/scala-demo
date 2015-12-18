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
    div(cls := "col-md-6", userTable),
    div(cls := "col-md-3")
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
            td(width := "30%", s"${user.username}"),
            td(width := "20%", b(s"${user.calories}")),
            td(width := "20%",
              input(tpe := "button", value := "Delete",
                onclick := { () => dispatch(Unregister(user.username)) }),
              input(tpe := "button", value := "Log As",
                onclick := { () => dispatch(Login(user.username, user.password)) })
            )))))
}

