package repositories

import models.{LetInSYM, UseOAuthCred, UseWSClient}
import utils.Reader
import utils.Reader._

object LetInSYMInterpreter {
  type Twitter[A] = Reader[UseWSClient with UseOAuthCred, A]

  implicit val letInInterpreter = new LetInSYM[Twitter] {
    def let[A, B](ta: => Twitter[A])(tf: Twitter[A => B]): Twitter[B] =
      for {
        a <- ta
        f <- tf
      } yield f(a)

    def in[A, B](f: Twitter[A] => Twitter[B]): Twitter[A => B] = {
      reader(e => (x: A) => f(pure(x)).run(e))
    }
  }

  def let[A, B](a: => Twitter[A])(f: Twitter[A => B])(implicit T: LetInSYM[Twitter]): Twitter[B] =
    T.let(a)(f)

  def in[A, B](f: Twitter[A] => Twitter[B])(implicit T: LetInSYM[Twitter]): Twitter[A => B] =
    T.in(f)
}
