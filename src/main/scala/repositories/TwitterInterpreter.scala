package repositories

import models._
import play.api.libs.ws.WSResponse
import utils.Reader._
import utils.{Reader, More, Done, Free}
import repositories.TwitterRepositoryDI._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object TwitterInterpreter {
  def runTwitter[A](dsl: Free[Twitter, A], env: UseWSClient with UseOAuthCred): Unit = dsl match {
    case Done(a) => ()
    case More(Fetch(screenName, f)) =>
      for {
        fws <- fetchUserByScreenName(screenName).run(env)
      } yield runTwitter(f(fws), env)
    case More(Update(status, next)) =>
      for {
        _ <- updateStatus(status).run(env)
      } yield runTwitter(next, env)
  }
}
