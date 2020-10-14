package modules

import javax.inject._
import javax.inject.Singleton
import repositories.UrlRepository

@Singleton
class StartDatabase @Inject() (urlRepository: UrlRepository) {
  urlRepository.start
}
