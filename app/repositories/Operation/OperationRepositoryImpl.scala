package repositories.Operation

import models.Operation.Operation
import models.Operation.OperationType.OperationType
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class OperationRepositoryImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends
  OperationRepository with OperationComponent with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val operationTable = TableQuery[Operations]

  override def getOperationByOperationType(operationType: OperationType)(implicit ex: ExecutionContext): Future[Operation] = {
    val query = operationTable.filter(_.operationType === operationType)

    dbConfig.db.run(query.result.head)
  }
}
