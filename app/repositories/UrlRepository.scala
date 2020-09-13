package repositories

import controllers.ShortenRequest
import database.Data
import database.Data._
import javax.inject.Singleton
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

@Singleton
class UrlRepository {
  val query = Data.urls.schema.createIfNotExists

  def get(alias: String): Future[Option[UrlEntity]] = {
    val query = urls.filter(_.shortUrl === alias).take(1).result.headOption
    db.run(query)
  }

  def save(request: ShortenRequest): Future[Int] = {
    val query = urls += UrlEntity(request.url, request.alias)
    db.run(query)
  }
}
