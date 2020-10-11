package database

import slick.jdbc.MySQLProfile.api._

object Data {

  val db = Database.forConfig("mydb")

  case class UrlEntity(originalUrl: String, shortUrl: String, id: Long = 0)

  class Urls(tag: Tag) extends Table[UrlEntity](tag, "URLS") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def originalUrl = column[String]("ORIGINAL_URL")
    def shortUrl = column[String]("SHORT_URL")
    def * = (originalUrl, shortUrl, id).mapTo[UrlEntity]
  }

  val urls = TableQuery[Urls]

}
