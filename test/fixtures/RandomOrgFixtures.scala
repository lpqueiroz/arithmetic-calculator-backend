package fixtures

import models.RandomString.RandomString
import repositories.RandomOrg.RandomOrgRepository

import scala.concurrent.{ExecutionContext, Future}

object RandomOrgFixtures {

  def randomOrgTestRepository(randomStrData: RandomString): RandomOrgRepository = {
    new RandomOrgRepository {
      override def getRandomString()(implicit ex: ExecutionContext): Future[RandomString] = Future.successful(randomStrData)
    }
  }
}
