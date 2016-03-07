package repositories

import models.{UseOAuthCred, UseWSClient}
import play.api.libs.ws.WSResponse
import utils.Reader
import utils.Reader._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TwitterRepositoryDI {
  def fetchUserByScreenName(screenName: String): Reader[UseWSClient with UseOAuthCred, Future[WSResponse]] =
    reader(env =>
      env.client.url("https://api.twitter.com/1.1/users/show.json")
        .withQueryString("screen_name" -> screenName)
        .sign(env.cred)
        .get())

  def updateStatus(status: String): Reader[UseWSClient with UseOAuthCred, Future[WSResponse]] =
    reader(env =>
      env.client.url("https://api.twitter.com/1.1/statuses/update.json")
        .sign(env.cred)
        .post(Map("status" -> Seq(status))))

  def existUserWithScreenName(screenName: String): Reader[UseWSClient with UseOAuthCred, Future[Boolean]] =
    reader(env =>
      for {
        res <- env.client.url("https://api.twitter.com/1.1/users/show.json")
          .withQueryString("screen_name" -> screenName)
          .sign(env.cred)
          .get()
      } yield
        res.status == 200
    )
}
