package utils

case class Coproduct[F[_], G[_], A](value: Either[F[A], G[A]])

object Coproduct {
  implicit def coproductFunctor[F[_], G[_]](implicit F: Functor[F], G: Functor[G]) =
    new Functor[({type L[A] = Coproduct[F, G, A]})#L] {
      def map[A, B](a: Coproduct[F, G, A])(f: A => B): Coproduct[F, G, B] = a.value match {
        case Left(e)  => Coproduct[F, G, B](Left(F.map(e)(f)))
        case Right(e) => Coproduct[F, G, B](Right(G.map(e)(f)))
      }
    }
}