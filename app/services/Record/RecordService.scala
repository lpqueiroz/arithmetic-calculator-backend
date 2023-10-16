package services.Record

import com.google.inject.ImplementedBy
import models.Record.{RecordId, RecordResponse}
import models.User.UserId

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[RecordServiceImpl])
trait RecordService {

  def listRecordsByUser(skip: Option[Int], limit: Option[Int], userId: UserId)
                           (implicit ex: ExecutionContext): Future[Seq[RecordResponse]]

  def deleteRecord(id: RecordId)(implicit ex: ExecutionContext): Future[Int]
}
