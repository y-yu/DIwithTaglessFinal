package repositories

import models._
import repositories.TwitterRepositoryDI._
import utils.{More, Done, Free}

import scala.concurrent.ExecutionContext.Implicits.global

object TwitterWithDeleteInterpreter {
  import models.TwitterWithDelete.TwitterWithDelete

  def runTwitterWithDelete[A](dsl: Free[TwitterWithDelete, A], env: UseWSClient with UseOAuthCred): Unit = dsl match {
    case Done(a) => ()
    case More(x) => x.value match {
      case Left(a) => a match {
        case Fetch(screenName, f) =>
          for {
            fws <- fetchUserByScreenName(screenName).run(env)
          } yield runTwitterWithDelete(f(fws), env)
        case Update(status, next) =>
          for {
            _ <- updateStatus(status).run(env)
          } yield runTwitterWithDelete(next, env)
      }
      case Right(b) => b match {
        case Delete(id, next) => ???
      }
    }
  }
}
