package calories

import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom._
import scalatags.JsDom.all._

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

/**
  * Created by msciab on 11/12/15.
  */
@JSExport("CaloriesApp")
object CaloriesApp extends JSApp {

  val mainView = new MainView(CaloriesCircuit)
  val loginView = new LoginView(
    CaloriesCircuit.zoom(_.user.ticket.left.get),
    CaloriesCircuit)

  @JSExport
  def main() {
    val root = document.getElementById("root")
    CaloriesCircuit.subscribe(() => render(root))
    CaloriesCircuit(Logout(0))
  }

  def render(root: Element) {

    val loggedIn = CaloriesCircuit.zoom(_.user).value.ticket.isRight

    val e = div(cls := "container",
      if (loggedIn)
        mainView.render
      else
        loginView.render
    ).render

    /* hash match {
       case "#table" => caloriesTableView.render
       case "#form" => caloriesFormView.render
       case _ => loginView.render
     }*/

    // clear and update contents

    root.innerHTML = ""
    root.appendChild(e)
  }
}
