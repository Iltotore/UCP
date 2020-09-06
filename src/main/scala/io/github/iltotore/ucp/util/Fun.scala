package io.github.iltotore.ucp.util

import scala.reflect.ClassTag

object Fun {

  def sideEffect[T](fun: T => Unit): T => T = input => {
    fun(input)
    input
  }

  def singleMatch[T : ClassTag, M <: T: ClassTag](ifTrue: M => Unit = empty, orElse: T => Unit = empty): T => Unit = {
    case sub: M => ifTrue(sub)
    case unknown: T => orElse(unknown)
  }

  def sideMatch[T : ClassTag, M <: T : ClassTag](ifTrue: M => Unit = empty, orElse: T => Unit = empty): T => T = sideEffect(singleMatch(ifTrue, orElse))

  def empty[T]: T => Unit = _ => {}
}
