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


  // a single meal
  case class Meal(date: String,
                  time: String,
                  meal: String,
                  calories: Int,
                  id: String = "")


  case class MealDelete(id: String = "")

  case class MealFilter(fromDate: Option[String] = None ,
                        fromTime: Option[String] = None,
                        toDate: Option[String] = None,
                        toTime: Option[String] = None)
  // meals
  case class Meals(meals: Array[Meal]= Array.empty)

  // responses
  case class LoggedUser(ticket: Either[String, Int]
                        , role: String = ""
                        , name: String = ""
                        , username: String = ""
                        , calories: Int = 0
                        , data: Either[Array[Register], Array[Meal]] = Left(Array.empty)
                       )

}

