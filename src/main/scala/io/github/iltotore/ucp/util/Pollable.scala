package io.github.iltotore.ucp.util

class Pollable[T](private var value: Option[T]) {

  def peekOption: Option[T] = value

  def peek: T = peekOption.get

  def pollOption: Option[T] = {
    val v = value
    value = Option.empty
    v
  }

  def poll: T = pollOption.get

  def put(value: T): Unit = this.value = Option(value)

  def isEmpty: Boolean = value.isEmpty

  def isDefined: Boolean = value.isDefined
}

object Pollable {

  def apply[T](value: T): Pollable[T] = new Pollable(Option(value))

  def empty[T]: Pollable[T] = new Pollable[T](Option.empty)
}