import models.DefaultEnvironment
import models.TwitterEnv
import utils.Reader
import utils.Reader._

import scala.concurrent.Future

// import repositories.TwitterRepository._
// import repositories.TwitterRepositoryDI._
import repositories.DefaultInterpreter._
import scala.concurrent.ExecutionContext.Implicits.global

object Main {
  def main(args: Array[String]): Unit = {
    // for {
    //   r1 <- twitter.fetchUserByScreenName("_yyu_")
    //   r2 <- twitter.updateStatus("test")
    // } yield {
    //   println(r2.body)
    //   twitter.close()
    // }

    //(for {
    //  f1 <- fetchUserByScreenName("_yyu_")
    //  f2 <- updateStatus("test2")
    //} yield for {
    //  r1 <- f1
    //  r2 <- f2
    //} yield {
    //  println(r1.body)
    //  println(r2.body)
    //}).run(DefaultEnvironment.defaultEnvironment)

    condition(
      existUserWithScreenName("yyu"),
      updateStatus("ok"),
      pure({println("ng"); Future.successful("ng")})
    ).run(DefaultEnvironment.defaultEnvironment)
  }
}
