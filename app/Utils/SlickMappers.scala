package Utils

import models.Operation.{OperationId, OperationType}
import models.Operation.OperationType.OperationType
import models.Record.RecordId
import models.User.Status.Status
import models.User.{Status, UserId}
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait SlickMappers extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  implicit def operationIdMapper: BaseColumnType[Option[OperationId]] =
    MappedColumnType.base[Option[OperationId], Long](
      operationId => operationId.get.value,
      operationId => Some(OperationId(operationId))
    )

  implicit def operationTypeMapper: BaseColumnType[OperationType] =
    MappedColumnType.base[OperationType, String](
      operationType => operationType.toString,
      s => OperationType.withName(s)
    )

  implicit def recordIdMapper: BaseColumnType[Option[RecordId]] =
    MappedColumnType.base[Option[RecordId], Long](
      recordId => recordId.get.value,
      recordId => Some(RecordId(recordId))
    )

  implicit def userIdMapper: BaseColumnType[Option[UserId]] =
    MappedColumnType.base[Option[UserId], Long](
      userId => userId.get.value,
      userId => Some(UserId(userId))
    )

  implicit def statusMapper: BaseColumnType[Status] =
    MappedColumnType.base[Status, String](
      status => status.toString,
      s => Status.withName(s)
    )
}
