package services


import fixtures.{ArithmeticCalculatorFixtures, TokenFixtures}
import models.Operation.{Operation, OperationId, OperationType}
import models.RandomString.RandomString
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import models.Record.{Record, RecordId, RecordResponse}
import models.User.UserId
import java.sql.Timestamp
import java.time.Instant
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future


class ArithmeticCalculatorServiceSpec(implicit ev: ExecutionEnv) extends Specification {

  val record = Record(Some(RecordId(1)), Some(OperationId(1)), Some(UserId(1)), BigDecimal.decimal(1), BigDecimal.decimal(2000), "4", Timestamp.from(Instant.now()))
  val operation = Operation(Some(OperationId(1)), OperationType.Addition, BigDecimal.decimal(5))
  val randomString = RandomString("asddlkj")
  val recordResponse = RecordResponse(RecordId(1), "addition", "test@test.com", BigDecimal.decimal(2000), "4", Timestamp.from(Instant.now()))

  case class TestData(record: ArrayBuffer[Record] = ArrayBuffer.empty,
                      successOrFailure: Future[Record] = Future.failed(new Exception()),
                      successOrFailureRandomStr: Future[RandomString] = Future.failed(new Exception()))

  "ArithmeticCalculatorService" >> {
    "performBasicOperation" >> {
      "should return an instance of Record" >> {
        val service = ArithmeticCalculatorFixtures.arithmeticCalculatorTestService(operation, record, randomString, recordResponse)

        val result = service.performBasicOperation(OperationType.Addition, 2, 2, UserId(1))

        result must beAnInstanceOf[Record].await
      }
    }
  }
}

