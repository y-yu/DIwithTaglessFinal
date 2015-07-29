package utils

case class Done[F[_]: Functor, A](a: A) extends Free[F, A]
case class More[F[_]: Functor, A](k: F[Free[F, A]]) extends Free[F, A]

class Free[F[_], A](implicit F: Functor[F]) {
  def flatMap[B](f: A => Free[F, B]): Free[F, B] = this match {
    case Done(a) => f(a)
    case More(k) => More[F, B](F.map(k)(_ flatMap f))
  }

  def map[B](f: A => B): Free[F, B] =
    flatMap(x => Done(f(x)))
}
