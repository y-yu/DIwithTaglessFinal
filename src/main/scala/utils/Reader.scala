package utils



case class Reader[E, A](g: E => A) {
  def apply(e: E) = g(e)
  def run: E => A = apply

  def map[B](f: A => B): Reader[E, B] = Reader(c => f(g(c)))

  def flatMap[B](f: A => Reader[E, B]): Reader[E, B] = Reader(c => f(g(c))(c))
}

object Reader {
  def pure[E, A](a: A): Reader[E, A] = Reader(c => a)

  def reader[E, A](f: E => A): Reader[E, A] = Reader(f)
}
