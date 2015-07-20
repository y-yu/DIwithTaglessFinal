import models.DefaultEnvironment
import services.UpdateStatusService._

object Main {
  def main(args: Array[String]): Unit = {
    updateStatusIfExist("_yyu_", "good").run(DefaultEnvironment.defaultEnvironment)
  }
}
