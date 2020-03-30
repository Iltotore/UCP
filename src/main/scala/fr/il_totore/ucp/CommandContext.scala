package fr.il_totore.ucp

import scala.collection.MultiDict


class CommandContext(args: MultiDict[String, _]) {

  def get[T](key: String): Set[T] = args.get(key).asInstanceOf[Set[T]]

  def getFirst[T](key: String): Option[T] = get(key).head

  def getLast[T](key: String): Option[T] = {
    val iterableOpt: Option[Set[T]] = Option(get(key))
    if (iterableOpt.isEmpty) return Option.empty
    Option(iterableOpt.get.last)
  }

  def getSlice[T](key: String, start: Int, end: Int): Iterable[T] = {
    get(key).slice(start, end)
  }

  def getAt[T](key: String, index: Int): Option[T] = {
    get(key).toList(index)
  }

  def putArgument[T](key: String, t: T): Unit = get[T](key)+=t
}
