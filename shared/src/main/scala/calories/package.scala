/**
  * Created by msciab on 12/12/15.
  */
package object calories {

  // requests
  case class Login(username: String, password: String)

  case class Logout(ticket: Int)

  case class Register(name: String,
                      username: String,
                      calories: Int,
                      password: String)

  case class Unregister(username: String)

  case class Users(users: Array[Register] = Array.empty)

  // a single meal
  case class Meal(date: String,
                  time: String,
                  meal: String,
                  calories: Int,
                  id: String = "",
                  isOver: Boolean = false)

  case class MealDelete(id: String = "")

  case object MealFilter

  // meals
  case class Meals(meals: Array[Meal] = Array.empty)

  // responses
  case class LoggedUser(ticket: Either[String, Int]
                        , role: String = ""
                        , name: String = ""
                        , username: String = ""
                        , calories: Int = 0
                        , data: Either[Array[Register], Array[Meal]] = Left(Array.empty)
                       )

}

