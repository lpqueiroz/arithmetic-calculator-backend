package repositories.RandomOrg

import com.typesafe.config.Config
import models.RandomString.RandomString
import play.api.Configuration
import play.api.libs.json.{JsObject, Json}
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RandomOrgRepositoryImpl @Inject()(ws: WSClient, val config: Configuration) extends RandomOrgRepository {

  private val apiKey = config.get[String]("randomorg.apiKey")

  override def getRandomString()(implicit ex: ExecutionContext): Future[RandomString] = {

    val url = "https://api.random.org/json-rpc/4/invoke"
    val characters = "abcdefghijklmnopqrstuvwxyz"

    val data = Json.obj(
      "jsonrpc" -> "2.0",
      "method" -> "generateStrings",
      "params" -> Json.obj(
        "apiKey" -> apiKey,
        "n" -> 1,
        "length" -> 7,
        "characters" -> characters,
        "replacement" -> true,
        "pregeneratedRandomization" -> null
      ),
      "id" -> 2658
    )

    for {
      apiResponse <- ws.url(url).post(data)
    } yield apiResponse.json.as[JsObject].fields.toMap.get("result")
      .flatMap(_.as[JsObject].fields.toMap.get("random")
        .flatMap(_.as[JsObject].fields.toMap.get("data")
          .map(_.as[Seq[String]]))).map(_.head).map(s => RandomString(s)).get
  }
}
