package controllers.ArithmeticCalculator

import auth.AuthAction
import handlers.ErrorHandlers
import models.Operation.OperationType
import models.User.UserId
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.ArithmeticCalculator.ArithmeticCalculatorService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ArithmeticCalculatorController @Inject()(val arithmeticCalculatorService: ArithmeticCalculatorService,
                                               val controllerComponents: ControllerComponents,
                                               val authAction: AuthAction)
                                              (implicit ex: ExecutionContext) extends I18nSupport with BaseController with ErrorHandlers {

  def performBasicCalculation(): Action[AnyContent] =  authAction.async {
    implicit request =>
      val userId = request.claims.getBody.getSubject
      CalculatorForm.basicCalculatorform.bindFromRequest.fold(
        errors => {
          Future.successful(BadRequest(errors.errorsAsJson))
        },
        form => arithmeticCalculatorService.performBasicOperation(OperationType.withName(form.operationType), form.firstInput, form.secondInput, UserId(userId.toLong))
          .map(operation => Ok(Json.toJson(operation)))
          .recover(exceptionHandlers)
      )
  }

  def performSquareRootCalculation(): Action[AnyContent] =  authAction.async {
    implicit request =>
      val userId = request.claims.getBody.getSubject
      CalculatorForm.squareRootCalculatorForm.bindFromRequest.fold(
        errors => {
          Future.successful(BadRequest(errors.errorsAsJson))
        },
        form => arithmeticCalculatorService.performSquareRootOperation(OperationType.withName(form.operationType), form.input, UserId(userId.toLong))
          .map(operation => Ok(Json.toJson(operation)))
          .recover(exceptionHandlers)
      )
  }

  def performRandomStringCalculation(): Action[AnyContent] = authAction.async {
    implicit request =>
      val userId = request.claims.getBody.getSubject
      arithmeticCalculatorService.performRandomStringOperation(UserId(userId.toLong))
        .map(response => Ok(Json.toJson(response)))
        .recover(exceptionHandlers)
  }
}
