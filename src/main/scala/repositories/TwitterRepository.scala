package repositories

import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws._
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcWSClient}

import scala.concurrent.Future

object TwitterRepository {
  val config = new AhcConfigBuilder().build()
  val client = new AhcWSClient(config)

  val key   = ConsumerKey(
    "key",
    "secret"
  )
  val token = RequestToken(
    "token",
    "secret"
  )

  def fetchUserByScreenName(screenName: String): Future[WSResponse] =
    client.url("https://api.twitter.com/1.1/users/show.json")
      .withQueryString("screen_name" -> screenName)
      .sign(OAuthCalculator(key, token))
      .get()

  def updateStatus(status: String): Future[WSResponse] =
    client.url("https://api.twitter.com/1.1/statuses/update.json")
      .sign(OAuthCalculator(key, token))
      .post(Map("status" -> Seq(status)))
}
