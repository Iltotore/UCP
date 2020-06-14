package io.github.il_totore.ucp.parsing

/**
 * Contains the original String and the produced arguments.
 *
 * @param raw    the original parsed String.
 * @param params the created arguments from the parser.
 */
class CommandArguments(raw: String, params: Vector[SingleParameter]) {

  private var index: Int = -1

  /**
   * Get the raw input.
   *
   * @return the original String.
   */
  def getRawInput: String = raw

  /**
   * Test if there is another argument.
   *
   * @return true if there is one or more arguments left.
   */
  def hasNext: Boolean = index < params.size - 1

  /**
   * Get the next argument.
   *
   * @return the next argument if exists or a None.
   */
  def next: Option[String] = {
    if (!hasNext) return Option.empty
    index += 1
    Option(params(index).getValue)
  }

  /**
   * Get the next argument without jumping to the next one.
   *
   * @return the next argument if exists or a None.
   */
  def peek: Option[String] = {
    if (!hasNext) return Option.empty
    Option(params(index + 1).getValue)
  }

  /**
   * Get the current index of this CommandArguments.
   *
   * @return the current index between 0 and the max size-1.
   */
  def getCurrentIndex: Int = index

  /**
   * Rollback this CommandArgument to the passed index.
   *
   * @param index the position to rollback on.
   */
  def reset(index: Int): Unit = this.index = index

  /**
   * Rollback this CommandArgument to the first element.
   */
  def reset(): Unit = reset(-1)

  /**
   * Rollback this CommandArguments n positions behind.
   *
   * @param n the number of index to rollback behind.
   */
  def back(n: Int): Unit = {
    index -= n
    if (index < -1) index = -1
  }
}