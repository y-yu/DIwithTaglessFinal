package repositories

import models.TwitterEnv
import models.TwitterSYM
import play.api.libs.ws.WSResponse
import utils.Reader
import utils.Reader._

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object DefaultInterpreter {
  type Twitter[A] = Reader[TwitterEnv, A]

  implicit val defaultInterpreter = new TwitterSYM[Twitter] {
    def existUserWithScreenName(screenName: Twitter[String]): Twitter[Future[Boolean]] =
      for {
        sn  <- screenName
        env <- ask
      } yield for {
        res <- env.client.url("https://api.twitter.com/1.1/users/show.json")
                .withQueryString("screen_name" -> sn)
                .sign(env.cred)
                .get()
      } yield
        if (res.status == 200) true
        else throw new RuntimeException("not found")

    def updateStatus(status: Twitter[String]): Twitter[Future[String]] =
      for {
        s   <- status
        env <- ask
      } yield for {
        res <- env.client.url("https://api.twitter.com/1.1/statuses/update.json")
                .sign(env.cred)
                .post(Map("status" -> Seq(s)))
        id  <- Future((res.json \ "id_str").as[String])
      } yield id


    def condition[A](cond: Twitter[Future[Boolean]], t: Twitter[A], e: Twitter[A]): Twitter[Future[A]] =
      for {
        f   <- cond
        env <- ask
      } yield for {
        c <- f
      } yield {
        if (c) t(env) else e(env)
      }
  }

  def existUserWithScreenName(screenName: Twitter[String])(implicit i: TwitterSYM[Twitter]): Twitter[Future[Boolean]] =
    i.existUserWithScreenName(screenName)

  def updateStatus(status: Twitter[String])(implicit i: TwitterSYM[Twitter]): Twitter[Future[String]] =
    i.updateStatus(status)

  def condition[A](c: Twitter[Future[Boolean]], t: Twitter[A], e: Twitter[A])(implicit i: TwitterSYM[Twitter]): Twitter[Future[A]] =
    i.condition(c, t, e)
}


