import com.google.inject.AbstractModule
import modules.StartDatabase

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[StartDatabase]).asEagerSingleton()
  }
}
