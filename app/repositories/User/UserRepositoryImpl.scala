package repositories.User

import models.User.{User, UserId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import play.api.Logging

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class UserRepositoryImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends
  UserRepository with UserComponent with HasDatabaseConfigProvider[JdbcProfile] with Logging {

  import profile.api._

  private val usersTable = TableQuery[Users]

  override def findUserByUsername(username: String)(implicit ex: ExecutionContext): Future[Option[User]] = {
    val query = usersTable.filter(u => u.username === username)

    dbConfig.db.run(query.result.headOption)
  }
}
