package controllers.ArithmeticCalculator

import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}

object CalculatorForm {

  case class BasicCalculatorForm(operationType: String, firstInput: Int, secondInput: Int)

  val basicCalculatorform = Form(
    mapping(
      "operation_type" -> nonEmptyText,
      "first_input" -> number,
      "second_input" -> number
    )(BasicCalculatorForm.apply)(BasicCalculatorForm.unapply)
  )

  case class SquareRootCalculatorForm(operationType: String, input: Int)

  val squareRootCalculatorForm = Form(
    mapping(
      "operation_type" -> nonEmptyText,
      "input" -> number
    )(SquareRootCalculatorForm.apply)(SquareRootCalculatorForm.unapply)
  )
}
