package calories

import diode.{Dispatcher, ModelR}

import scalatags.JsDom.all._

/**
  * Created by msciab on 11/12/15.
  */
class CaloriesTableView(dispatch: Dispatcher) {

  def caloriesTable = table(cls := "table table-bordered",
    thead(
      tr(
        th("Date"),
        th("Time"),
        th("Meal"),
        th("Calories")
      )),
    tbody(
      for (j <- 1 to 10)
        yield tr(
          td(s"Date ${j}"),
          td(s"Time ${j}"),
          td(s"Meal ${j}"),
          td(s"Calories ${j}")
        )))

  def render =
    div(cls := "row",
      div(cls := "col-md-2"),
      div(cls := "col-md-6",
        caloriesTable,
        div(cls := "col-md-2")))
}



