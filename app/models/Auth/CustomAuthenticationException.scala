package models.Auth

class CustomAuthenticationException(message: String) extends Exception(message)

case class AuthenticationFailedException() extends CustomAuthenticationException(
  "Authentication failed!"
)