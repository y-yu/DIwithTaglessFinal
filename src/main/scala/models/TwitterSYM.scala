package models

import play.api.libs.ws.WSResponse

trait TwitterSYM[R[_]] {
  def string(str: String): R[String]
  def fetch(screenName: R[String]): R[WSResponse]
  def getScreenName(str: R[WSResponse]): R[String]
  def update(status: R[String]): R[String]
}

//def condition[A](cond: R[Boolean], t: R[A], e: R[A]): R[A]
