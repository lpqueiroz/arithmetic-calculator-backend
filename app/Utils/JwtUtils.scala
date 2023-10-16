package Utils

import io.jsonwebtoken.{Claims, Jws, Jwts, SignatureAlgorithm}
import java.util.Base64
import javax.crypto.spec.SecretKeySpec
import scala.util.Try

object JwtUtils {

  def validateJwt(jwtString: String, jwtSecret: String): Try[Jws[Claims]] = {
    Try {
      val hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret),
        SignatureAlgorithm.HS256.getJcaName());

      val jwt: Jws[Claims] = Jwts.parserBuilder()
        .setSigningKey(hmacKey)
        .build()
        .parseClaimsJws(jwtString);

      jwt
    }
  }
}
