package models

import play.api.libs.oauth.OAuthCalculator
import play.api.libs.ws.WSClient

case class TwitterEnv(client: WSClient, cred: OAuthCalculator)