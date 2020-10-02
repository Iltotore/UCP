package io.github.iltotore.ucp

package object util {

  implicit class OptionOps[T](base: Option[T]) {

    def flatFallback[U](newValue: => Option[U]): Option[U] = if(base.isEmpty) newValue else Option.empty

    def fallback[U](newValue: => U): Option[U] = flatFallback{Option(newValue)}
  }
}
