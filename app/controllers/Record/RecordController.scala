package controllers.Record

import auth.AuthAction
import handlers.ErrorHandlers
import models.Record.RecordId
import models.User.UserId
import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.Record.RecordService

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class RecordController @Inject()(val recordService: RecordService,
                                 val controllerComponents: ControllerComponents,
                                 val authAction: AuthAction)
                                (implicit ex: ExecutionContext) extends I18nSupport with BaseController with ErrorHandlers with Logging {

  def listRecordsByUser(skip: Option[Int], limit: Option[Int]): Action[AnyContent] =  authAction.async {
    implicit request =>
      val userId = request.claims.getBody.getSubject
      recordService.listRecordsByUser(skip, limit, UserId(userId.toLong))
        .map(records => Ok(Json.toJson(records)))
        .recover(exceptionHandlers)
  }

  def deleteRecord(id: Long) = authAction.async {
    implicit request =>
      recordService.deleteRecord(RecordId(id)).map(_ => NoContent)
  }
}