import io.circe.Decoder
import io.circe.parser.decode
import play.api.mvc.{AnyContent, Request}

import scala.concurrent.Future

package object controllers {

  /** Type used for asynchronous error and response handling */
  type ApplicationResult[T] = Future[Either[AppError, T]]

  /** Simple body decoder using circe */
  def decodeBody[T](
      request: Request[AnyContent]
  )(implicit decoder: Decoder[T]): Option[T] = {
    request.body.asJson.map(_.toString).map(decode[T]) collect {
      case Right(parsedBody) => parsedBody
    }
  }

  /** All error types handled by the application */
  sealed trait AppError
  case object DuplicatedAlias extends AppError
  case object NotFound extends AppError

  /**
    * Data Transfer Objects
    */
  case class ErrorMessage(msj: String)
  case class Response(url: String)
  case class ShortenRequest(url: String, alias: String)

}
