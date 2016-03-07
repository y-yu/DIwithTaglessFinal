package repositories

import models.{DeleteSYM, UseOAuthCred, UseWSClient}
import utils.Reader
import utils.Reader._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DeleteSYMInterpreter {
  type Twitter[A] = Reader[UseWSClient with UseOAuthCred, A]

  implicit val deleteInterpreter = new DeleteSYM[Twitter] {
    def delete(id: Twitter[String]): Twitter[Boolean] =
      for {
        idStr <- id
        env   <- ask
      } yield {
        val res = Await.result(
          env.client.url(s"https://api.twitter.com/1.1/statuses/destroy/$idStr.json")
            .sign(env.cred)
            .post(Map("id" -> Seq(idStr))),
          Duration.Inf
        )

        res.status == 200
      }
  }

  def delete(id: Twitter[String])(implicit T: DeleteSYM[Twitter]): Twitter[Boolean] =
    T.delete(id)
}
