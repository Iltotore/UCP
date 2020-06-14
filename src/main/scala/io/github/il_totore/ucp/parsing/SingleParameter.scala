package io.github.il_totore.ucp.parsing

/**
 * Represents a String and its position.
 *
 * @param value the parameter's value.
 * @param index the parameter's index.
 */
class SingleParameter(value: String, index: Int) {

  /**
   * Get the parameter's value
   *
   * @return the parameter's String value.
   */
  def getValue: String = value

  /**
   * Get the parameter's starting index.
   *
   * @return the index of the parameter's first char.
   */
  def getStartIndex: Int = index

  /**
   * Get the parameter's end index.
   *
   * @return the index after the parameter's last char.
   */
  def getEndIndex: Int = index + value.length

  override def toString: String = getValue
}
