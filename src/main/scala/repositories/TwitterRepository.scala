package repositories

import com.ning.http.client.AsyncHttpClientConfig
import play.api.libs.oauth.ConsumerKey
import play.api.libs.oauth.OAuthCalculator
import play.api.libs.oauth.RequestToken
import play.api.libs.ws._
import play.api.libs.ws.ning._

import scala.concurrent.Future

object TwitterRepository {
  val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build()
  val builder = new AsyncHttpClientConfig.Builder(config)
  val client = new NingWSClient(builder.build)

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

  def close() = {
    client.close()
  }
}
