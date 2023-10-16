package repositories.User

import com.google.inject.ImplementedBy
import models.User.{User, UserId}

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {

  def findUserByUsername(username: String)(implicit ex: ExecutionContext): Future[Option[User]]

}
