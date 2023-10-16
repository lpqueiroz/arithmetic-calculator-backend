package models.Operation

import models.Operation.OperationType.OperationType
import play.api.libs.json._

object OperationType extends Enumeration {

  implicit object someFormat extends Format[Value] {
    def writes(o: Value): JsValue = JsString(o.toString)

    def reads(json: JsValue): JsResult[Value] = json match {
      case JsString(x) => JsSuccess(withName(x.toLowerCase.capitalize))
      case _ => JsError()
    }
  }

  type OperationType = Value

  val Addition = Value("addition")
  val Subtraction = Value("subtraction")
  val Multiplication = Value("multiplication")
  val Division = Value("division")
  val SquareRoot = Value("square_root")
  val RandomString = Value("random_string")
}

case class OperationId(value: Long)

object OperationId {
  implicit val operationIdFormat = Json.format[OperationId]
}

case class Operation(
                      id: Option[OperationId],
                      operationType: OperationType,
                      cost: BigDecimal
                    )

object Operation {

  implicit val operationFormat = Json.format[Operation]

  def buildOperation(operationType: OperationType, cost: BigDecimal) = apply(None, operationType, cost)
}
