package fixtures

import models.Operation.Operation
import models.Operation.OperationType.OperationType
import repositories.Operation.OperationRepository

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}

object OperationFixtures {

  def operationTestRepository(operationData: Operation): OperationRepository = {
    new OperationRepository {
      override def getOperationByOperationType(operationType: OperationType)(implicit ex: ExecutionContext): Future[Operation] = Future.successful(operationData)
    }
  }

}
