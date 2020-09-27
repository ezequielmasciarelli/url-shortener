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

@Singleton
class UrlRepository {

  private def getQuery(alias: String) =
    urls.filter(_.shortUrl === alias).take(1).result.headOption

  private def insertIfNotExists(request: ShortenRequest) =
    getQuery(request.alias).flatMap {
      case Some(_) => DBIO.successful(0)
      case None    => urls += UrlEntity(request.url, request.alias)
    }.transactionally

  def get: String => ApplicationResult[UrlEntity] = { alias =>
    db.run(getQuery(alias)) map {
      case Some(value) => Right(value)
      case None        => Left(NotFound)
    }
  }

  def save: ShortenRequest => ApplicationResult[Unit] = { request =>
    db.run(insertIfNotExists(request)) map {
      case 1 => Right(())
      case _ => Left(DuplicatedAlias)
    }
  }
}
