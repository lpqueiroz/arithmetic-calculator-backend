package services.Auth

import com.typesafe.config.Config
import io.jsonwebtoken.{Jwts, SignatureAlgorithm}
import models.Auth.{AuthenticationFailedException, JwtToken}
import org.mindrot.jbcrypt.BCrypt
import play.api.{Configuration, Logging}
import repositories.User.UserRepository

import java.time.{LocalDate, ZoneId}
import java.util.{Base64, Date}
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.naming.AuthenticationException
import scala.concurrent.{ExecutionContext, Future}

class AuthServiceImpl @Inject()(val userRepository: UserRepository, val config: Configuration) extends AuthService with Logging {

  private val jwtSecret = config.get[String]("jwt.secretKey")
  private val hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret),
    SignatureAlgorithm.HS256.getJcaName());

  private val dateNow = LocalDate.now()
  private val expirationDate = dateNow.plusDays(1)

  override def login(username: String, password: String)(implicit ex: ExecutionContext): Future[JwtToken] = {
    for {
      userOpt <- userRepository.findUserByUsername(username)
      userCanLogin <- userOpt match {
        case Some(user) => Future.successful(BCrypt.checkpw(password, user.password))
        case None => Future.failed(AuthenticationFailedException())
      }
      token <- if (userCanLogin) {
        val t = Jwts.builder()
          .setSubject(userOpt.get.id.get.value.toString)
          .signWith(hmacKey)
          .setExpiration(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant)).compact()
        Future.successful(JwtToken(t))
      } else Future.failed(AuthenticationFailedException())
    } yield token
  }
}