package models

import utils.Functor

sealed trait DeleteOfTwitter[A]

case class Delete[A](id: String, next: A) extends DeleteOfTwitter[A]

object DeleteOfTwitter {
  implicit val deleteFunctor: Functor[DeleteOfTwitter] = new Functor[DeleteOfTwitter] {
    def map[A, B](a: DeleteOfTwitter[A])(f: A => B): DeleteOfTwitter[B] = a match {
      case Delete(id, next) => Delete(id, f(next))
    }
  }
}
