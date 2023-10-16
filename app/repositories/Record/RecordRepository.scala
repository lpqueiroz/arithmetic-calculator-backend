package repositories.Record

import com.google.inject.ImplementedBy
import models.Record.{RecordId, RecordResponse}
import models.User.UserId

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[RecordRepositoryImpl])
trait RecordRepository {

  def createRecord(record: models.Record.Record): Future[models.Record.Record]

  def listRecordsByUser(skip: Option[Int], limit: Option[Int], userId: Option[UserId])(implicit ex: ExecutionContext): Future[Seq[RecordResponse]]

  def deleteRecord(id: Option[RecordId]): Future[Int]

  def getLatestRecord(): Future[models.Record.Record]

}
