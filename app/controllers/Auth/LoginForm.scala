package controllers.Auth

import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}

object LoginForm {

  case class LoginForm(username: String, password: String)

  val form = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
