package controllers

import io.circe.generic.auto._
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

  def shorten: Action[AnyContent] =
    Action.async { implicit request =>
      decodeBody[ShortenRequest](request)
        .map { body =>
          urlRepository
            .save(body)
            .map {
              case Right(_)              => Ok(s"http://localhost:9000/api/${body.alias}")
              case Left(DuplicatedAlias) => BadRequest("El alias ya existe")
              case _                     => InternalServerError
            }
        }
        .getOrElse(Future.successful(BadRequest))
    }

  def get(alias: String): Action[AnyContent] =
    Action.async {
      urlRepository.get(alias) map {
        case Right(value) => MovedPermanently(value.originalUrl)
        case Left(error)  => NotFound
      }
    }
}
