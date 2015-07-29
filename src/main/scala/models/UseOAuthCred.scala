package models

import play.api.libs.oauth.OAuthCalculator

trait UseOAuthCred {
  val cred: OAuthCalculator
}
