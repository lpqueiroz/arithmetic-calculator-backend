package repositories.RandomOrg

import com.google.inject.ImplementedBy
import models.RandomString.RandomString

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[RandomOrgRepositoryImpl])
trait RandomOrgRepository {

  def getRandomString()(implicit ex: ExecutionContext): Future[RandomString]
}
