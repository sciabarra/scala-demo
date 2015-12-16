package calories

import diode.Dispatcher

import diode.{Dispatcher, ModelR}
import scalatags.JsDom.all._

/**
  * Created by msciab on 16/12/15.
  */
class MainView(dispatch: Dispatcher) {

  def render = div(cls := "row",
    div(cls := "col-md-3", caloriesForm),
    //div(cls := "col-md-1"),
    div(cls := "col-md-7",
      caloriesFilter,
      caloriesTable))

  def caloriesForm = form(role := "form",
    h2("New meal:"),
    div(cls := "form-group",
      label(`for` := "meal", "Meal"),
      input(id := "meal", tpe := "text", cls := "form-control")
    ),
    div(cls := "form-group",
      label(`for` := "calories", "Calories"),
      input(id := "calories", tpe := "number", min := "0", cls := "form-control")
    ),
    div(cls := "form-group",
      label(`for` := "date", "Date"),
      div(cls := "input-group",
        input(id := "date", tpe := "text", cls := "form-control input-small"),
        span(cls := "input-group-addon",
          i(cls := "glyphicon glyphicon-calendar")))
    ),
    div(cls := "form-group",
      label(`for` := "time", "Time"),
      div(cls := "input-group bootstrap-timepicker timepicker",
        input(id := "time", tpe := "text", cls := "form-control input-small"),
        span(cls := "input-group-addon",
          i(cls := "glyphicon glyphicon-time")))
    ),
    button("Add", `type` := "submit",
      cls := "btn btn-lg btn-primary btn-block"),

    script(tpe := "text/javascript")(raw(
      """$(function() {
            $("#date").datepicker({ format: 'mm-dd-yyyy' });
            $("#time").timepicker();
           });
      """))
  )


  def caloriesTable = table(cls := "table table-bordered",
    thead(
      tr(
        th("Date/Time"),
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

  def caloriesFilter = form(role := "form", cls := "form-inline",
    div(cls := "form-group col-md-3",
      input(id := "datefilter1", tpe := "text",
        cls := "form-control", placeholder := "Start date")),
    div(cls := "form-group col-md-3",
      input(id := "timefilter1", tpe := "text",
        cls := "form-control", placeholder := "Start time")),
    div(cls := "form-group col-md-3",
      input(id := "datefilter2", tpe := "text",
        cls := "form-control", placeholder := "End date")),
    div(cls := "form-group col-md-3",
      input(id := "timefilter2", tpe := "text",
        cls := "form-control", placeholder := "End time")),
    script(tpe := "text/javascript")(raw(
      """$(function() {
            $("#datefilter1").datepicker({ format: 'mm-dd-yyyy' });
            $("#datefilter2").datepicker({ format: 'mm-dd-yyyy' });
            $("#timefilter1").timepicker();
            $("#timefilter2").timepicker();
           });
      """))
  )
}

