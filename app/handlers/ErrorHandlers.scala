package handlers

import models.Auth.AuthenticationFailedException
import models.Record.InsuficientFundsException
import play.api.mvc.Result
import play.api.mvc.Results.{Forbidden, InternalServerError, Unauthorized}

trait ErrorHandlers {

  def handleInsuficientFundException: PartialFunction[Throwable, Result] = {
    case e: InsuficientFundsException => Forbidden(e.getMessage)
  }

  def handleAuthenticationException: PartialFunction[Throwable, Result] = {
    case e: AuthenticationFailedException => Unauthorized(e.getMessage)
  }

  def handleDefaultException: PartialFunction[Throwable, Result] = {
    case _ => InternalServerError("An error in the server occurred")
  }

  def exceptionHandlers: PartialFunction[Throwable, Result] =
    handleInsuficientFundException orElse
      handleAuthenticationException
}
