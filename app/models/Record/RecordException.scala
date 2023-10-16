package models.Record

class RecordException(message: String) extends Exception(message)

case class InsuficientFundsException(userId: Long) extends RecordException(
  s"User $userId does not have sufficient funds to perform this operation"
)
