package fixtures

import models.Operation.Operation
import models.Operation.OperationType.OperationType
import models.Record
import models.Record.{Record, RecordId, RecordResponse}
import models.User.UserId
import repositories.Operation.OperationRepository
import repositories.Record.RecordRepository

import java.sql.Timestamp
import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}
import models.Record.Record

import scala.collection.mutable.ArrayBuffer

object RecordFixtures {

  def recordTestRepository(recordData: Record, recordResponse: RecordResponse): RecordRepository = {

    new RecordRepository {
      override def createRecord(record: models.Record.Record): Future[Record] = Future.successful(recordData)

      override def listRecordsByUser(skip: Option[Int], limit: Option[Int], userId: Option[UserId])(implicit ex: ExecutionContext): Future[Seq[RecordResponse]] = Future.successful(ArrayBuffer(recordResponse).toSeq)

      override def deleteRecord(id: Option[RecordId]): Future[Int] = ???

      override def getLatestRecord(): Future[models.Record.Record] = Future.successful(recordData)
    }
  }
}
