package io.github.il_totore.ucp

import scala.collection.mutable

/**
 * A CommandContext includes the executed command and the passed arguments.
 *
 * @param spec the executed CommandSpec.
 * @param args the passed arguments.
 * @tparam S the sender's generic type.
 */
class CommandContext[S](spec: CommandSpec[S], args: mutable.MultiDict[String, Any]) {

  /**
   * Get all values from the passed key as Set[T].
   *
   * @param key the key associated with the wanted values.
   * @tparam T the type to cast.
   * @return a Set[T] of values.
   */
  def get[T](key: String): collection.Set[T] = args.get(key).asInstanceOf[collection.Set[T]]

  /**
   * Get the single value from the passed key as Option[T]
   *
   * @param key the key associated with the wanted value
   * @tparam T the type to cast.
   * @return an Option[T]. If the passed key is associated with multiples elements, the first will be picked. This is not deterministic!
   */
  def getFirst[T](key: String): Option[T] = args.get(key).headOption.asInstanceOf[Option[T]]

  /**
   * Get the last value from the passed key as Option[T].
   *
   * @param key the key associated with the wanted value.
   * @tparam T the type to cast.
   * @return an Option[T]. This is the same behavior than get[T](key).last
   */
  def getLast[T](key: String): Option[T] = {
    val iterableOpt: Option[collection.Set[T]] = Option(get(key))
    if (iterableOpt.isEmpty) return Option.empty
    Option(iterableOpt.get.last)
  }

  /**
   * Get a slice of the values set as Iterable[T]
   *
   * @param key   the key associated with the wanted values.
   * @param start the starting index.
   * @param end   the ending index.
   * @tparam T the type to cast.
   * @return an Iterable[T] containing values.
   */
  def getSlice[T](key: String, start: Int, end: Int): Iterable[T] = {
    get(key).slice(start, end)
  }

  /**
   * Put an entry containing a key and an argument. Mainly used while parsing arguments.
   *
   * @param key   the key mapping the value.
   * @param value the value of any type.
   */
  def putArgument(key: String, value: Any): Unit = args.addOne((key, value))

  /**
   * Get the context's spec.
   *
   * @return the executed CommandSpec[S]
   */
  def getSpec: CommandSpec[S] = spec

  /**
   * Execute the context using the given sender.
   *
   * @param sender the command sender of type S.
   * @return the result returned by the command execution.
   */
  def execute(sender: S): GeneralResult = spec.getExecutor.apply(sender, this)
}