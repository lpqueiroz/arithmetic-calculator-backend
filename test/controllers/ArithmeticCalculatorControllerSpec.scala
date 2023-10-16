package controllers

import akka.actor.ActorSystem
import akka.stream.{Materializer, SystemMaterializer}
import auth.AuthAction
import com.typesafe.config.ConfigFactory
import controllers.ArithmeticCalculator.ArithmeticCalculatorController
import fixtures.JwtFixture.MockJwtRequest
import fixtures.{ArithmeticCalculatorFixtures, TokenFixtures}
import models.RandomString.RandomString
import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import models.Record.Record
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.BodyParsers
import play.api.test.Helpers._
import auth.AuthService

import java.sql.Timestamp
import java.time.LocalDateTime
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future


class ArithmeticCalculatorControllerSpec(implicit ev: ExecutionEnv) extends Specification {

  implicit val materialzer = SystemMaterializer(ActorSystem()).materializer

  case class TestData(record: ArrayBuffer[Record] = ArrayBuffer.empty,
                      successOrFailure: Future[Record] = Future.failed(new Exception()),
                      successOrFailureRandomStr: Future[RandomString] = Future.failed(new Exception()))

  def arithmeticCalculatorController(_testData: TestData) = new ArithmeticCalculatorController(
    ArithmeticCalculatorFixtures.arithmeticCalculatorStubService(
      _testData.successOrFailure
    ), stubControllerComponents(),
    new AuthAction(new BodyParsers.Default(), new AuthService(new Configuration(ConfigFactory.load())))
  )

  "ArithmeticCalculatorController" >> {
    "performBasicCalculation" >> {
      "should return 200 OK" >> {
        val record = Record(None, None, None, BigDecimal.decimal(123), BigDecimal.decimal(123), "5", Timestamp.valueOf(LocalDateTime.now()))
        val randomString = RandomString("abcfdse")
        val controller: ArithmeticCalculatorController = arithmeticCalculatorController(TestData(ArrayBuffer(record), Future.successful(record),Future.successful(randomString)))

        val request = MockJwtRequest.withToken(Some(TokenFixtures.token)).withJsonBody((Json.parse(
          """{
            |"operation_type": "division",
            |"first_input": 10,
            |"second_input": 2
            |}""".stripMargin
        )))

        val result = controller.performBasicCalculation()(request)

        status(result) must beEqualTo(200)
      }
      "should return 401 UNAUTHORIZED" >> {
        val record = Record(None, None, None, BigDecimal.decimal(123), BigDecimal.decimal(123), "5", Timestamp.valueOf(LocalDateTime.now()))
        val randomString = RandomString("abcfdse")
        val controller: ArithmeticCalculatorController = arithmeticCalculatorController(TestData(ArrayBuffer(record), Future.successful(record),Future.successful(randomString)))

        val request = MockJwtRequest.withToken(Some("randomtoken")).withJsonBody((Json.parse(
          """{
            |"operation_type": "division",
            |"first_input": 10,
            |"second_input": 2
            |}""".stripMargin
        )))

        val result = controller.performBasicCalculation()(request)

        status(result) must beEqualTo(401)
      }
      "should return 400 BAD REQUEST" >> {
        val record = Record(None, None, None, BigDecimal.decimal(123), BigDecimal.decimal(123), "5", Timestamp.valueOf(LocalDateTime.now()))
        val randomString = RandomString("abcfdse")
        val controller: ArithmeticCalculatorController = arithmeticCalculatorController(TestData(ArrayBuffer(record), Future.successful(record),Future.successful(randomString)))

        val request = MockJwtRequest.withToken(Some(TokenFixtures.token)).withJsonBody((Json.parse(
          """{
            |"operation_type": "division",
            |"first_input": 10
            |}""".stripMargin
        )))

        val result = controller.performBasicCalculation()(request)

        status(result) must beEqualTo(400)
      }
    }
  }
}
