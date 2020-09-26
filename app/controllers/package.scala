import scala.concurrent.Future

package object controllers {
  case class ShortenRequest(url: String, alias: String)
  type ApplicationResult[T] = Future[Either[AppError, T]]

  trait AppError
  case object DuplicatedAlias extends AppError
  case object NotFound extends AppError
}
