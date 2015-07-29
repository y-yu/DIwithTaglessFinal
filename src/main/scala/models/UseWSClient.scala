package models

import play.api.libs.ws.WSClient

trait UseWSClient {
  val client: WSClient
}
