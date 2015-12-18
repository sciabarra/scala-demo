package calories

import diode.{Dispatcher, ModelR}
import scalatags.JsDom.all._
import org.scalajs.jquery.{jQuery => $}

/**
  * Created by msciab on 16/12/15.
  */
class MealView(meals: ModelR[Array[Meal]], dispatch: Dispatcher) {

  val date = new scala.scalajs.js.Date()
  val today = "%04d-%02d-%02d".format(date.getFullYear(), date.getMonth() + 1, date.getDay())

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
          value := today,
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
    button("Logout", `type` := "button",
      cls := "btn btn-lg btn-primary btn-block",
      onclick := { () => dispatch(Logout(0)) }
    ),
    script(tpe := "text/javascript")(raw(
      """$(function() {
            $("#date").datepicker({
              format: 'yyyy-mm-dd',
              autoclose: true,
              immediateUpdates: true,
              showToday: true
            });
            $("#time").timepicker({
               showMeridian: false,
               showSeconds: true
            });
           });
      """))
  )

  def caloriesTable = div(cls := "panel panel-default",
    table(cls := "table table-bordered",
      thead(tr(
        th("Date/Time"),
        th("Meal"),
        th("Calories")
      )), tbody(
        for (meal <- meals.value)
          yield tr(
            td(width := "20%", i(s"${meal.date} ${meal.time}")),
            td(width := "40%", s"${meal.meal}"),
            td(width := "15%", b(s"${meal.calories}")),
            td(width := "15%",
              input(tpe := "button", value := "Delete",
                onclick := { () => dispatch(MealDelete(meal.id)) }))))))

  def caloriesFilter = form(role := "form", cls := "form-inline",
    div(cls := "form-group col-md-3",
      input(id := "datefilter1", tpe := "text", value := today,
        cls := "form-control", placeholder := "Start date")),
    div(cls := "form-group col-md-3",
      input(id := "timefilter1", tpe := "text", value := "00:00",
        cls := "form-control", placeholder := "Start time")),
    div(cls := "form-group col-md-3",
      input(id := "datefilter2", tpe := "text",
        cls := "form-control", placeholder := "End date")),
    div(cls := "form-group col-md-3",
      input(id := "timefilter2", tpe := "text",
        cls := "form-control", placeholder := "End time")),
    script(tpe := "text/javascript")(raw(
      """$(function() {
            $("#datefilter1").datepicker({ format: 'yyyy-mm-dd' });
            $("#datefilter2").datepicker({ format: 'yyyy-mm-dd' });
            $("#timefilter1").timepicker({
              defaultTime: false,
              showMeridian: false,
              template: "dropdown",
              appendWidgetTo: "#timefilter1"
            });
            $("#timefilter2").timepicker({
              defaultTime: false,
              showMeridian: false,
              template: "dropdown",
              appendWidgetTo: "#timefilter2"
            });
           });
      """))
  )
}

