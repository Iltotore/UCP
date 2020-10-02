package io.github.iltotore.ucp

import java.util.NoSuchElementException

import scala.collection.mutable

/**
  * An interface allowing argument storing.
  */
trait CommandContext {

  /**
    * Associate a key with a value, used when parsing.
    *
    * @param key   the key used to retrieve the given value.
    * @param value the value marked by the given key.
    */
  def putArgument(key: String, value: Any): Unit

  /**
    * Safely get the value of type T using the given key.
    *
    * @param key the key used to retrieve the value.
    * @tparam T the value type.
    * @return an Option[T] eventually containing the value associated with the given key.
    */
  def get[T](key: String): Option[T]

  /**
    * Get the value of type T using the given key or throw an exception if absent.
    *
    * @param key the key used to retrieve the wanted value.
    * @tparam T the value type.
    * @throws NoSuchElementException if there is no value of type T represented by the given key.
    * @return the researched value as T if present.
    */
  def apply[T](key: String): T = get(key).getOrElse {
    throw new NoSuchElementException(s"Argument $key not found")
  }
}

object CommandContext {


  /**
    * A CommandContext implementation storing arguments in a Map[String, Any].
    * @param map the mutable.Map instance where arguments are stored.
    */
  class Mapped(map: mutable.Map[String, Any]) extends CommandContext {

    override def putArgument(key: String, value: Any): Unit = map.put(key, value)

    override def get[T](key: String): Option[T] = map.get(key).asInstanceOf[Option[T]]
  }

}