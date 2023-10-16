package models.RandomString

import play.api.libs.json.{Json, OFormat, Reads}

case class RandomString(data: String)

object RandomString {

  implicit val format: OFormat[RandomString] = Json.format[RandomString]
}

case class RandomData(random: RandomString, bitsUsed: Int, bitsLeft: Int, requestLeft: Int, advisoryDelay: Int)

object RandomData {

  implicit val format: OFormat[RandomData] = Json.format[RandomData]
}

case class RandomOrgResponse(jsonRpc: String, result: RandomData, id: Int)

object RandomOrgResponse {

  implicit val format: OFormat[RandomOrgResponse] = Json.format[RandomOrgResponse]

  implicit val reads: Reads[RandomOrgResponse] = Json.reads[RandomOrgResponse]
}
