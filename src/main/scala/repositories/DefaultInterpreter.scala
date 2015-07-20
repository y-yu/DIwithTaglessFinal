package repositories

import models.TwitterSYM
import models.UseOAuthCred
import models.UseWSClient
import utils.Reader
import utils.Reader._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DefaultInterpreter {
  type Twitter[A] = Reader[UseWSClient with UseOAuthCred, A]

  implicit val defaultInterpreter = new TwitterSYM[Twitter] {
    def existUserWithScreenName(screenName: Twitter[String]): Twitter[Boolean] =
      for {
        sn  <- screenName
        env <- ask
      } yield {
        val res = Await.result(
                    env.client.url("https://api.twitter.com/1.1/users/show.json")
                      .withQueryString("screen_name" -> sn)
                      .sign(env.cred)
                      .get(),
                    Duration.Inf
                  )

        res.status == 200
      }

    def updateStatus(status: Twitter[String]): Twitter[String] =
      for {
        s   <- status
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

    def condition[A](cond: Twitter[Boolean], t: Twitter[A], e: Twitter[A]): Twitter[A] = {
      for {
        bool <- cond
        env  <- ask
      } yield
        if (bool) t(env) else e(env)
    }
  }

  def pure[A](a: A): Twitter[A] = Reader.pure[UseWSClient with UseOAuthCred, A](a)

  def existUserWithScreenName(screenName: Twitter[String])(implicit i: TwitterSYM[Twitter]): Twitter[Boolean] =
    i.existUserWithScreenName(screenName)

  def updateStatus(status: Twitter[String])(implicit i: TwitterSYM[Twitter]): Twitter[String] =
    i.updateStatus(status)

  def condition[A](c: Twitter[Boolean], t: Twitter[A], e: Twitter[A])(implicit i: TwitterSYM[Twitter]): Twitter[A] =
    i.condition(c, t, e)
}


