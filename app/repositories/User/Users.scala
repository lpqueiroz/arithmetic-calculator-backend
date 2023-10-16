package repositories.User

import Utils.SlickMappers
import models.User.Status.Status
import models.User.{User, UserId}
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait UserComponent extends HasDatabaseConfigProvider[JdbcProfile] with SlickMappers {
  import profile.api._

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Option[UserId]]("id", O.PrimaryKey, O.AutoInc)

    def username = column[String]("username")

    def password = column[String]("password")

    def status = column[Status]("status")

    override def * = (
      id,
      username,
      password,
      status
    ) <> ((User.apply _).tupled, User.unapply)
  }
}
