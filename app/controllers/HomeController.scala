package controllers

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import javax.inject._
import play.api.mvc._
import repositories.UrlRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject() (
    val controllerComponents: ControllerComponents,
    val urlRepository: UrlRepository
) extends BaseController {

  def index(): Action[AnyContent] =
    Action { implicit request: Request[AnyContent] =>
      Ok(views.html.index())
    }

  def decodeBody[T](
      request: Request[AnyContent]
  )(implicit decoder: Decoder[T]): Option[T] = {
    request.body.asJson.map(_.toString).map(decode[T]) collect {
      case Right(parsedBody) => parsedBody
    }
  }

  def shorten(): Action[AnyContent] =
    Action.async { implicit request =>
      decodeBody[ShortenRequest](request)
        .map { body =>
          urlRepository
            .save(body)
            .map(_ => Ok)
        }
        .getOrElse(Future.successful(BadRequest))
    }

  def get(alias: String): Action[AnyContent] =
    Action.async {
      urlRepository.get(alias).map {
        case Some(value) => Ok(value.asJson.toString)
        case None        => NotFound
      }
    }

  def getWithRedirect(alias: String): Action[AnyContent] =
    Action.async {
      urlRepository.get(alias).map {
        case Some(value) => MovedPermanently(value.originalUrl)
        case None        => NotFound
      }
    }
}
