package repositories

import models.TwitterSYM
import models.UseOAuthCred
import models.UseWSClient
import play.api.libs.ws.WSResponse
import utils.Reader
import utils.Reader._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object TwitterSYMInterpreter {
  type Twitter[A] = Reader[UseWSClient with UseOAuthCred, A]

  implicit val twitterSYMInterpreter = new TwitterSYM[Twitter] {
    def string(str: String): Twitter[String] = pure(str)

    def fetch(screenName: Twitter[String]): Twitter[WSResponse] =
      for {
        sn <- screenName
        env <- ask
      } yield {
        Await.result(
          env.client.url("https://api.twitter.com/1.1/users/show.json")
            .withQueryString("screen_name" -> sn)
            .sign(env.cred)
            .get(),
          Duration.Inf
        )
      }

    def getScreenName(res: Twitter[WSResponse]): Twitter[String] =
      for {
        raw <- res
        env <- ask
      } yield (raw.json \ "screen_name").as[String]

    def update(status: Twitter[String]): Twitter[String] =
      for {
        s <- status
        env <- ask
      } yield {
        val res = Await.result(
          env.client.url("https://api.twitter.com/1.1/statuses/update.json")
            .sign(env.cred)
            .post(Map("status" -> Seq(s))),
          Duration.Inf
        )

        (res.json \ "id_str").as[String]
      }
  }
  //  def condition[A](cond: Twitter[Boolean], t: Twitter[A], e: Twitter[A]): Twitter[A] = {
  //    for {
  //      bool <- cond
  //      env <- ask
  //    } yield
  //    if (bool) t(env) else e(env)
  //  }
  //}

  //def pure[A](a: A): Twitter[A] = Reader.pure[UseWSClient with UseOAuthCred, A](a)

  def string(str: String)(implicit T: TwitterSYM[Twitter]): Twitter[String] =
    T.string(str)

  def fetch(screenName: Twitter[String])(implicit T: TwitterSYM[Twitter]): Twitter[WSResponse] =
    T.fetch(screenName)

  def getScreeName(res: Twitter[WSResponse])(implicit T: TwitterSYM[Twitter]): Twitter[String] =
    T.getScreenName(res)

  def update(status: Twitter[String])(implicit T: TwitterSYM[Twitter]): Twitter[String] =
    T.update(status)

  // def condition[A](c: Twitter[Boolean], t: Twitter[A], e: Twitter[A])(implicit i: TwitterSYM[Twitter]): Twitter[A] =
  //   i.condition(c, t, e)
}


