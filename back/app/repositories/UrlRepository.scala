package repositories

import controllers.{
  ApplicationResult,
  DuplicatedAlias,
  NotFound,
  ShortenRequest
}
import database.Data._
import javax.inject.Singleton
import slick.dbio.Effect
import slick.jdbc.MySQLProfile.api._
import slick.sql.SqlAction

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UrlRepository {

  def start: Future[Unit] = db.run(urls.schema.createIfNotExists)

  private def getQuery(
      alias: String
  ): SqlAction[Option[UrlEntity], NoStream, Effect.Read] =
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
