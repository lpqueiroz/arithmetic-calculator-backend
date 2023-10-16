package services.ArithmeticCalculator

import com.google.inject.ImplementedBy
import models.Operation.OperationType.OperationType
import models.RandomString.RandomString
import models.Record.Record
import models.User.UserId

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[ArithmeticCalculatorServiceImpl])
trait ArithmeticCalculatorService {

  def performBasicOperation(operationType: OperationType, firstInput: Int, secondInput: Int, userId: UserId)(implicit ex: ExecutionContext): Future[Record]

  def performSquareRootOperation(operationType: OperationType, input: Int, userId: UserId)(implicit ex: ExecutionContext): Future[Record]

  def performRandomStringOperation(userId: UserId)(implicit ex: ExecutionContext): Future[Record]
}
