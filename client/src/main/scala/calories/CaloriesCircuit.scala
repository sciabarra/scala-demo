package calories

import diode.Circuit

/**
  * Created by msciab on 09/12/15.
  */
object CaloriesCircuit extends Circuit[CaloriesModel] {

  val msg = """Please login or register.""".stripMargin

  protected var model: CaloriesModel = CaloriesModel(LoggedUser(Left(msg)), Array.empty[Meal])

  protected def loginHandler = new LoginHandler(
    zoomRW(m => m.user) // extraction fn
      ((m, v) => m.copy(user = v))) //  update fn

  protected def mealHandler = new MealHandler(
    zoomRW(m => m.user) // extraction fn
      ((m, v) => m.copy(user = v))) //  update fn

  override def actionHandler = combineHandlers(loginHandler, mealHandler)
}
