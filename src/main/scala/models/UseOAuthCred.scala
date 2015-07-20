package models

import play.api.libs.oauth.OAuthCalculator

/**
 * Created by hikaru_yoshimura on 2015/07/20.
 */
trait UseOAuthCred {
  val cred: OAuthCalculator
}
