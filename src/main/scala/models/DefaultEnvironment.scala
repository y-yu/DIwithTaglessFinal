package models

import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcWSClient}

object DefaultEnvironment {
  val config  = new AhcConfigBuilder().build()
  val c       = new AhcWSClient(config)

  val defaultEnvironment = new UseWSClient with UseOAuthCred {
    val client = c
    val cred = OAuthCalculator(
      ConsumerKey(
        "key",
        "secret"
      ),
      RequestToken(
        "token",
        "secret"
      )
    )
  }
}