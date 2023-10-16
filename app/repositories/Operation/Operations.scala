package repositories.Operation

import Utils.SlickMappers
import models.Operation.{Operation, OperationId}
import models.Operation.OperationType.OperationType
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait OperationComponent extends HasDatabaseConfigProvider[JdbcProfile] with SlickMappers {
  import profile.api._

  class Operations(tag: Tag) extends Table[Operation](tag, "operations") {
    def id = column[Option[OperationId]]("id", O.PrimaryKey, O.AutoInc)

    def operationType = column[OperationType]("type")

    def cost = column[BigDecimal]("cost")

    override def * = (
      id,
      operationType,
      cost
    ) <> ((Operation.apply _).tupled, Operation.unapply)
  }
}
