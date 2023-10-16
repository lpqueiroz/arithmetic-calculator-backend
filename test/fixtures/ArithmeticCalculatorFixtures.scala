package fixtures

import models.Operation.Operation
import models.Operation.OperationType.OperationType
import models.RandomString.RandomString

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}
import models.Record.{Record, RecordResponse}
import models.User.UserId
import services.ArithmeticCalculator.{ArithmeticCalculatorService, ArithmeticCalculatorServiceImpl}

object ArithmeticCalculatorFixtures {

  def arithmeticCalculatorStubService[A](result: Future[Record]): ArithmeticCalculatorService = new ArithmeticCalculatorService {
    override def performBasicOperation(operationType: OperationType, firstInput: Int, secondInput: Int, userId: UserId)(implicit ex: ExecutionContext): Future[Record] = result

    override def performSquareRootOperation(operationType: OperationType, input: Int, userId: UserId)(implicit ex: ExecutionContext): Future[Record] = result

    override def performRandomStringOperation(userId: UserId)(implicit ex: ExecutionContext): Future[Record] = result
  }

  def arithmeticCalculatorTestService(operation: Operation, record: Record, randomString: RandomString, recordResponse: RecordResponse) = new ArithmeticCalculatorServiceImpl(
    OperationFixtures.operationTestRepository(operation),
    RecordFixtures.recordTestRepository(record, recordResponse),
    RandomOrgFixtures.randomOrgTestRepository(randomString)
  )

}
