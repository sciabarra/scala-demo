package calories

/**
  * Created by msciab on 20/12/15.
  */
trait Common {
  val form = Map("Content-Type" -> "application/x-www-form-urlencoded")
  val json = Map("Content-Type" -> "application/json")
  val date = new scala.scalajs.js.Date()
  val today = "%04d-%02d-%02d".format(date.getFullYear(), date.getMonth()+1, date.getDate())

}
