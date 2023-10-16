package services.Auth

import com.google.inject.ImplementedBy
import models.Auth.JwtToken

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[AuthServiceImpl])
trait AuthService {

  def login(username: String, password: String)(implicit ex: ExecutionContext): Future[JwtToken]

}
