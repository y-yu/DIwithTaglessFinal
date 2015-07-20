package models

import play.api.libs.ws.WSClient

/**
 * Created by hikaru_yoshimura on 2015/07/20.
 */
trait UseWSClient {
  val client: WSClient
}
