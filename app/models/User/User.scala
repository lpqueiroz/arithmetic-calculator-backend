package models.User

import models.User.Status.Status
import play.api.libs.json.Json

case class UserId(value: Long)

object UserId {

  implicit val userIdFormat = Json.format[UserId]
}

case class User(
                id: Option[UserId],
                username: String,
                password: String,
                status: Status
               )

object User {

}

object Status extends Enumeration {
  type Status = Value

  val Active = Value("active")
  val Inactive = Value("inactive")
}
