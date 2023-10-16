package repositories.Record

import Utils.SlickMappers
import models.Operation.OperationId
import models.Record.RecordId
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.Record
import models.User.UserId

import java.sql.Timestamp

trait RecordsComponent extends HasDatabaseConfigProvider[JdbcProfile] with SlickMappers {
  import profile.api._

  class Records(tag: Tag) extends Table[models.Record.Record](tag, "records") {
    def id = column[Option[RecordId]]("id", O.PrimaryKey, O.AutoInc)

    def operationId = column[Option[OperationId]]("operation_id")

    def userId = column[Option[UserId]]("user_id")

    def amount = column[BigDecimal]("amount")

    def userBalance = column[BigDecimal]("user_balance")

    def operationResponse = column[String]("operation_response")

    def date = column[Timestamp]("date")

    def deletedAt = column[Option[Timestamp]]("deleted_at")

    override def * = (
      id,
      operationId,
      userId,
      amount,
      userBalance,
      operationResponse,
      date,
      deletedAt
    ) <> ((Record.Record.apply _).tupled, Record.Record.unapply)
  }
}
