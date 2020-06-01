package io.github.il_totore.ucp

import scala.collection.mutable


class CommandContext[S](spec: CommandSpec[S], args: mutable.MultiDict[String, Any]) {

  def get[T](key: String): collection.Set[T] = args.get(key).asInstanceOf[collection.Set[T]]

  def getFirst[T](key: String): Option[T] = args.get(key).headOption.asInstanceOf[Option[T]]

  def getLast[T](key: String): Option[T] = {
    val iterableOpt: Option[collection.Set[T]] = Option(get(key))
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

  def getSpec: CommandSpec[S] = spec

  def execute(sender: S): GeneralResult = spec.getExecutor.apply(sender, this)
}