package models.Auth

import play.api.libs.json.Json

case class JwtToken(token: String)

object JwtToken {

  implicit val jsonTokenFormat = Json.format[JwtToken]
}
