package repositories.Operation

import com.google.inject.ImplementedBy
import models.Operation.Operation
import models.Operation.OperationType.OperationType

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[OperationRepositoryImpl])
trait OperationRepository {

  def getOperationByOperationType(operationType: OperationType)(implicit ex: ExecutionContext): Future[Operation]

}
