package calories

import diode.{Dispatcher, ModelR}
import scalatags.JsDom.all._
import org.scalajs.jquery.{jQuery => $}

/**
  * Created by msciab on 16/12/15.
  */
class MealView(meals: ModelR[Array[Meal]], dispatch: Dispatcher) {

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
      input(id := "meal", tpe := "text",
        value := "Meal",
        cls := "form-control")
    ),
    div(cls := "form-group",
      label(`for` := "calories", "Calories"),
      input(id := "calories", tpe := "number", min := "0",
        value := "100",
        cls := "form-control")
    ),
    div(cls := "form-group",
      label(`for` := "date", "Date"),
      div(cls := "input-group",
        input(id := "date", tpe := "text",
          value := {
            val date = new scala.scalajs.js.Date()
            "%04d-%02d-%02d".format(date.getFullYear(), date.getMonth()+1, date.getDay())
          },
          cls := "form-control input-small"),
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
    button("Add", `type` := "button",
      cls := "btn btn-lg btn-primary btn-block",
      onclick := { () => dispatch(Meal(
        date = $("#date").value.toString,
        time = $("#time").value.toString,
        meal = $("#meal").value.toString,
        calories = Integer.parseInt($("#calories").value.toString)))
      }
    ),

    script(tpe := "text/javascript")(raw(
      """$(function() {
            $("#date").datepicker({
              format: 'mm-dd-yyyy',
              autoclose: true,
              immediateUpdates: true,

            });
            $("#time").timepicker();
           });
      """))
  )

  def caloriesTable = div(cls := "panel panel-default",
    table(cls := "table table-bordered",
      thead(
        tr(
          th("Date/Time"),
          th("Meal"),
          th("Calories")
        )),
      tbody(
        for (meal <- meals.value)
          yield tr(
            td(s"${meal.date} ${meal.time}"),
            td(s"${meal.meal}"),
            td(s"Meal ${meal.calories}")
          ))))

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

