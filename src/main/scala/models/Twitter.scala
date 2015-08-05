package models

import play.api.libs.ws.WSResponse
import utils._

sealed trait Twitter[A]

case class Fetch[A](screenName: String, next: WSResponse => A) extends Twitter[A]
case class Update[A](status: String, next: A) extends Twitter[A]
//case class Delete[A](id: String, next: A) extends Twitter[A]

object Twitter {
  implicit val twitterFunctor = new Functor[Twitter] {
    def map[A, B](a: Twitter[A])(f: A => B) = a match {
      case Fetch(screenName, next) => Fetch(screenName, x => f(next(x)))
      case Update(status, next)    => Update(status, f(next))
    }
  }

  def fetch[A](screenName: String, f: WSResponse => Free[Twitter, A]): Free[Twitter, A] =
    More(Fetch(screenName, f))

  def update(status: String): Free[Twitter, Unit] =
    More(Update(status, Done()))
}

