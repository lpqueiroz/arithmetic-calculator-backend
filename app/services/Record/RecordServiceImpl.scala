package services.Record

import models.Record.{RecordId, RecordResponse}
import models.User.UserId
import repositories.Record.RecordRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RecordServiceImpl @Inject()(val recordRepository: RecordRepository) extends RecordService {


  override def listRecordsByUser(skip: Option[Int], limit: Option[Int], userId: UserId)
                                (implicit ex: ExecutionContext): Future[Seq[RecordResponse]] = {


    recordRepository.listRecordsByUser(skip, limit, Some(userId))
  }

  override def deleteRecord(id: RecordId)(implicit ex: ExecutionContext): Future[Int] =
    {
      recordRepository.deleteRecord(Some(id))
    }
}
