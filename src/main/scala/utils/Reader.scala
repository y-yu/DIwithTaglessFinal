package utils

case class Reader[E, A](g: E => A) {
  def apply(e: E) = g(e)
  def run: E => A = apply

  def map[B](f: A => B): Reader[E, B] = Reader(e => f(g(e)))

  def flatMap[B](f: A => Reader[E, B]): Reader[E, B] = Reader(e => f(g(e))(e))
}

object Reader {
  def pure[E, A](a: A): Reader[E, A] = Reader(e => a)

  def ask[E]: Reader[E, E] = Reader(identity)

  def local[E, A](f: E => E, c: Reader[E, A]): Reader[E, A] = Reader(e => c(f(e)))

  implicit def reader[E, A](f: E => A): Reader[E, A] = Reader(f)
}
