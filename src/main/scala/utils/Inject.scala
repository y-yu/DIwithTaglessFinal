package utils


trait Inject[F[_], G[_]] {
  def inj[A](sub: F[A]): G[A]
}

object Inject {
  implicit def reflexive[F[_]: Functor] = new Inject[F, F] {
    def inj[A](a: F[A]): F[A] = a
  }

  implicit def left[F[_]: Functor, G[_]: Functor] =
    new Inject[F, ({type L[A] = Coproduct[F, G, A]})#L] {
      def inj[A](a: F[A]): Coproduct[F, G, A] = Coproduct[F, G, A](Left(a))
    }

  implicit def right[F[_]: Functor, G[_]: Functor, H[_]: Functor](implicit I: Inject[F, G]) =
    new Inject[F, ({type L[A] = Coproduct[H, G, A]})#L] {
      def inj[A](a: F[A]): Coproduct[H, G, A] = Coproduct[H, G, A](Right(I.inj(a)))
    }
}
