package io.github.il_totore.ucp.parsing

/**
 * Used for parse a command line to an instance of CommandArguments.
 */
trait InputTokenizer {

  /**
   * Get the command name from the command line.
   *
   * @param text the command line as String.
   * @return the found command name.
   */
  def getCommandName(text: String): String

  /**
   * Extract a List of SingleParameter from the given String.
   *
   * @param text the given command line
   * @return the extracted parameters.
   */
  def tokenize(text: String): Vector[SingleParameter]
}
