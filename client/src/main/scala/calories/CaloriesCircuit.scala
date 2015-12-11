package calories

import diode.Circuit

case class CaloriesModel(user: Option[String])

/**
  * Created by msciab on 09/12/15.
  */
object CaloriesCircuit extends Circuit[CaloriesModel] {

  protected var model: CaloriesModel = CaloriesModel(None)

  protected def loginHandler = new LoginHandler(
    zoomRW(m => m.user) // extraction fn
      ((m, v) => m.copy(user = v))) //  update fn

  override def actionHandler = combineHandlers(loginHandler)
}
