package calories

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

  val loginView = new LoginView(CaloriesCircuit.zoom(_.user), CaloriesCircuit)

  @JSExport
  def main()  {
    val root = document.getElementById("root")
    CaloriesCircuit.subscribe( () => render(root))
    CaloriesCircuit(Logout)
  }

  def render(root: Element) = {
    val e = div(cls := "container",
      h1("Welcome"),
      loginView.render
    ).render
    // clear and update contents
    root.innerHTML = ""
    root.appendChild(e)
  }
}
