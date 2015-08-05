package models

import utils._

object TwitterWithDelete {
  import models.DeleteOfTwitter.deleteFunctor
  import models.Twitter.twitterFunctor
  import utils.Coproduct.coproductFunctor
  import utils.Inject._

  type TwitterWithDelete[A] = Coproduct[Twitter, DeleteOfTwitter, A]

  def left[A](l: Twitter[A]): Either[Twitter[A], DeleteOfTwitter[A]] = Left(l)
  def right[A](r: Delete[A]): Either[Twitter[A], DeleteOfTwitter[A]] = Right(r)
  def coproduct[A](a: Either[Twitter[A], DeleteOfTwitter[A]]) = Coproduct[Twitter, DeleteOfTwitter, A](a)
  def more[A](k: TwitterWithDelete[Free[TwitterWithDelete, A]]): Free[TwitterWithDelete, A] = More[TwitterWithDelete, A](k)
  def done[A](a: A): Free[TwitterWithDelete, A] = Done[TwitterWithDelete, A](a)

  val example: Free[TwitterWithDelete, Unit] =
    more(coproduct(left(Update("new tweet", more(coproduct(right(Delete("<id>", done()))))))))

  def inject[A](a: Twitter[A])(implicit I: Inject[Twitter, TwitterWithDelete]) =
    I.inj(a)
  def inject[A](a: DeleteOfTwitter[A])(implicit I: Inject[DeleteOfTwitter, TwitterWithDelete]) =
    I.inj(a)

  val example2: Free[TwitterWithDelete, Unit] =
    more(inject(Update("new tweet", more(inject(Delete("<id>", done()))))))
}