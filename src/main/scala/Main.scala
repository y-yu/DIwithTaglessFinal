import models.DefaultEnvironment
import services.UpdateStatusService._

import scala.concurrent.Future

// import repositories.TwitterRepository._
// import repositories.TwitterRepositoryDI._
import scala.concurrent.ExecutionContext.Implicits.global

object Main {
  def main(args: Array[String]): Unit = {
    updateStatusIfExist("_yyu_", "ok").run(DefaultEnvironment.defaultEnvironment)
  }
}
