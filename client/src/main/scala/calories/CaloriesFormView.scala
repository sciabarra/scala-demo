package calories

import diode.Dispatcher

import scalatags.JsDom.all._

/**
  * Created by msciab on 11/12/15.
  */
class CaloriesFormView(dispatch: Dispatcher) {

  def caloriesForm =
    form(role := "form",
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
      script(tpe := "text/javascript")(raw(
        """$(function() {
            $("#date").datepicker({
              format: 'mm-dd-yyyy'
             });
            $("#time").timepicker();
           });
        """))
    )

  def render =
    div(cls := "row",
      div(cls := "col-md-2"),
      div(cls := "col-md-6",
        caloriesForm,
        div(cls := "col-md-2")))


}



