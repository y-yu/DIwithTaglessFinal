package models

trait TwitterSYM[R[_]] {
  def existUserWithScreenName(screenName: R[String]): R[Boolean]
  def updateStatus(status: R[String]): R[String]
  def condition[A](cond: R[Boolean], t: R[A], e: R[A]): R[A]
}
