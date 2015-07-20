package models

import play.api.libs.ws.WSResponse
import utils.Reader

import scala.concurrent.Future

trait TwitterSYM[R[_]] {
  def existUserWithScreenName(screenName: R[String]): R[Future[Boolean]]
  def updateStatus(status: R[String]): R[Future[String]]
  def condition[A](cond: R[Future[Boolean]], t: R[A], e: R[A]): R[Future[A]]
}
