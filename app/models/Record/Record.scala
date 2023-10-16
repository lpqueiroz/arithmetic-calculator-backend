package models.Record

import models.Operation.OperationId
import models.User.UserId
import play.api.libs.json._
import java.sql.Timestamp
import java.text.SimpleDateFormat

case class RecordId(value: Long)

object RecordId {

  implicit val recordIdFormat = Json.format[RecordId]
}

case class Record(
                  id: Option[RecordId],
                  operationId: Option[OperationId],
                  userId: Option[UserId],
                  amount: BigDecimal,
                  userBalance: BigDecimal,
                  operationResponse: String,
                  date: Timestamp,
                  deletedAt: Option[Timestamp] = None
                 )

object Record {

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val format = Json.format[Record]
}

case class RecordResponse(recordId: RecordId, operationType: String, username: String, userBalance: BigDecimal, operationResponse: String, date: Timestamp)

object RecordResponse {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val format = Json.format[RecordResponse]
}
