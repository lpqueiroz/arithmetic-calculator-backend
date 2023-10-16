package auth

import io.jsonwebtoken.{Claims, Jws, Jwts, SignatureAlgorithm}
import play.api.{Configuration, Logging}

import java.util.Base64
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import scala.util.Try

class AuthService @Inject()(config: Configuration) extends Logging {

  def validateJwt(jwtString: String): Try[Jws[Claims]] = {
    Try {
      val hmacKey = new SecretKeySpec(Base64.getDecoder.decode(config.get[String]("jwt.secretKey")),
        SignatureAlgorithm.HS256.getJcaName)

      val jwt: Jws[Claims] = Jwts.parserBuilder()
        .setSigningKey(hmacKey)
        .build()
        .parseClaimsJws(jwtString);

      jwt
    }
  }
}
