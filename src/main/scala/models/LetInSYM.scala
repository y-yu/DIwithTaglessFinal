package models

trait LetInSYM[R[_]] {
  def let[A, B](a: => R[A])(l: R[A => B]): R[B]
  def in[A, B](a: R[A] => R[B]): R[A => B]
}
