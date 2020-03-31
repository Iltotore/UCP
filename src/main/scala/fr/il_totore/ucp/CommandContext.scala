package fr.il_totore.ucp

import scala.collection.mutable


class CommandContext(args: mutable.MultiDict[String, Any]) {

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

  def putArgument(key: String, value: Any): Unit = args.addOne((key, value))
}
