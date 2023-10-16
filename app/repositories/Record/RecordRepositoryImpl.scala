package repositories.Record

import models.Record.{Record, RecordId, RecordResponse}
import models.User.UserId
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import repositories.Operation.OperationComponent

import scala.concurrent.{ExecutionContext, Future}
import repositories.User.UserComponent

import java.sql.Timestamp
import java.time.Instant


class RecordRepositoryImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends
  RecordRepository with RecordsComponent with OperationComponent with UserComponent with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val recordsTable = TableQuery[Records]
  private val operationsTable = TableQuery[Operations]
  private val usersTable = TableQuery[Users]


  override def createRecord(record: models.Record.Record): Future[models.Record.Record] = {
    val insertQuery = (recordsTable returning recordsTable.map(_.id) into {
      case (t, id) => Record(id, t.operationId, t.userId, t.amount, t.userBalance, t.operationResponse, t.date)
    }) += record

    dbConfig.db.run(insertQuery)
  }

  override def listRecordsByUser(skip: Option[Int], limit: Option[Int], userId: Option[UserId])(implicit ex: ExecutionContext): Future[Seq[RecordResponse]] = {

    val datatable = for {
      records <- recordsTable if records.deletedAt.isEmpty
      users <- usersTable if records.userId === users.id
      operations <- operationsTable if records.operationId === operations.id
    } yield (records.id, users.id, operations.operationType, users.username, records.userBalance, records.operationResponse, records.date)

    val query = datatable.filter(_._2 === userId).sortBy(_._7.desc)

    val paginatedQuery = (skip, limit) match {
      case (Some(s), Some(l)) => query.drop(s).take(l)
      case (Some(s), None) => query.drop(s)
      case (None, Some(l)) => query.take(l)
      case (None, None) => query
    }

    dbConfig.db.run(
      paginatedQuery.result
    ).map(_.map {
        case (Some(recordId), _, operationType, username, balance, operationResponse, date) => RecordResponse(recordId, operationType.toString, username, balance, operationResponse, date)
    })
  }

  override def deleteRecord(id: Option[RecordId]): Future[Int] = {
    dbConfig.db.run(
      recordsTable.filter(_.id === id)
        .map(r => (r.deletedAt))
        .update((Some(Timestamp.from(Instant.now()))))
    )
  }

  override def getLatestRecord(): Future[Record] = {
    dbConfig.db.run(
      recordsTable.sortBy(_.date.desc).result.head
    )
  }
}
