package controllers.Auth

import handlers.ErrorHandlers
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.Auth.AuthService
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthController @Inject()(val authService: AuthService,
                               val controllerComponents: ControllerComponents)
                              (implicit ex: ExecutionContext) extends I18nSupport with BaseController with ErrorHandlers {

  def login(): Action[AnyContent] =  Action.async {
    implicit request =>
      LoginForm.form.bindFromRequest.fold(
        errors => {
          Future.successful(BadRequest(errors.errorsAsJson))
        },
        form => authService.login(form.username, form.password)
          .map(token  => Ok(Json.toJson(token)))
          .recover(exceptionHandlers)
      )
  }
}
