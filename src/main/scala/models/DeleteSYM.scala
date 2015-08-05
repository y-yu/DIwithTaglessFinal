package models

trait DeleteSYM[R[_]] {
  def delete(id: R[String]): R[Boolean]
}
