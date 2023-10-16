package services.ArithmeticCalculator

import models.Operation.OperationType
import models.Operation.OperationType.OperationType
import models.Record.{InsuficientFundsException, Record}
import models.User.UserId
import repositories.Operation.OperationRepository
import repositories.RandomOrg.RandomOrgRepository
import repositories.Record.RecordRepository
import java.sql.Timestamp
import java.time.Instant
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ArithmeticCalculatorServiceImpl @Inject()(val operationRepository: OperationRepository,
                                                val recordRepository: RecordRepository,
                                                val randomOrgRepository: RandomOrgRepository) extends ArithmeticCalculatorService {


  override def performBasicOperation(operationType: OperationType, firstInput: Int, secondInput: Int, userId: UserId)
                                    (implicit ex: ExecutionContext): Future[models.Record.Record] = {
    for {
      latestRecord <- recordRepository.getLatestRecord()
      currentUserBalance <- Future.successful(latestRecord.userBalance)
      operation <- operationRepository.getOperationByOperationType(operationType)
      updatedUserBalance <- if (currentUserBalance - operation.cost >= 0)
        Future.successful(currentUserBalance - operation.cost) else Future.failed(InsuficientFundsException(userId.value))
      operationResponse <- operation.operationType match {
        case OperationType.Multiplication =>  Future.successful(firstInput * secondInput)
        case OperationType.Addition => Future.successful(firstInput + secondInput)
        case OperationType.Subtraction => Future.successful(firstInput - secondInput)
        case OperationType.Division => Future.successful(firstInput / secondInput)
      }
      record <- recordRepository.createRecord(
        Record(
          None,
          operation.id,
          Some(userId),
          operation.cost,
          updatedUserBalance,
          operationResponse.toString,
          Timestamp.from(Instant.now())
        )
      )
    } yield record
  }

  override def performSquareRootOperation(operationType: OperationType, input: Int, userId: UserId)(implicit ex: ExecutionContext): Future[Record] = {
    for {
      latestRecord <- recordRepository.getLatestRecord()
      currentUserBalance <- Future.successful(latestRecord.userBalance)
      operation <- operationRepository.getOperationByOperationType(operationType)
      updatedUserBalance <- if (currentUserBalance - operation.cost >= 0)
        Future.successful(currentUserBalance - operation.cost) else Future.failed(InsuficientFundsException(userId.value))
      operationResponse <- operation.operationType match {
        case OperationType.SquareRoot =>  Future.successful(Math.sqrt(input))
      }
      record <- recordRepository.createRecord(
        Record(
          None,
          operation.id,
          Some(userId),
          operation.cost,
          updatedUserBalance,
          operationResponse.toString,
          Timestamp.from(Instant.now())
        )
      )
    } yield record
  }

  override def performRandomStringOperation(userId: UserId)(implicit ex: ExecutionContext): Future[Record] = {
    for {
      latestRecord <- recordRepository.getLatestRecord()
      currentUserBalance <- Future.successful(latestRecord.userBalance)
      randomStr <- randomOrgRepository.getRandomString()
      operation <- operationRepository.getOperationByOperationType(OperationType.RandomString)
      updatedUserBalance <- if (currentUserBalance - operation.cost >= 0)
        Future.successful(currentUserBalance - operation.cost) else Future.failed(InsuficientFundsException(userId.value))
      record <- recordRepository.createRecord(
        Record(
          None,
          operation.id,
          Some(userId),
          operation.cost,
          updatedUserBalance,
          randomStr.data,
          Timestamp.from(Instant.now())
        )
      )
    } yield record
  }
}
