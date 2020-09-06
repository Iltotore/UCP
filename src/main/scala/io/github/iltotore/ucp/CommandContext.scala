package io.github.iltotore.ucp

import scala.collection.mutable

trait CommandContext {

  def putArgument(key: String, value: Any): Unit

  def get[T](key: String): Option[T]

  def apply[T](key: String): T = get(key).getOrElse{throw new NoSuchElementException(s"Argument $key not found")}
}

object CommandContext {

  class Mapped(map: mutable.Map[String, Any]) extends CommandContext {

    override def putArgument(key: String, value: Any): Unit = map.put(key, value)

    override def get[T](key: String): Option[T] = map.get(key).asInstanceOf[Option[T]]
  }
}