package repositories

import controllers.{
  ApplicationResult,
  DuplicatedAlias,
  NotFound,
  ShortenRequest
}
import database.Data._
import javax.inject.Singleton
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UrlRepository {

  def get: String => ApplicationResult[UrlEntity] = { alias =>
    val query = urls.filter(_.shortUrl === alias).take(1).result.headOption
    db.run(query) map {
      case Some(value) => Right(value)
      case None        => Left(NotFound)
    }
  }

  def exists: String => Future[Boolean] =
    get andThen { futureRow =>
      for {
        row <- futureRow
      } yield row.isRight
    }

  def save: ShortenRequest => ApplicationResult[Unit] = { request =>
    val query = urls += UrlEntity(request.url, request.alias)
    for {
      hasDuplicatedAlias <- exists(request.alias)
      result <- if (!hasDuplicatedAlias) db.run(query) else Future.successful(0)
    } yield if (result == 1) Right(()) else Left(DuplicatedAlias)
  }
}
