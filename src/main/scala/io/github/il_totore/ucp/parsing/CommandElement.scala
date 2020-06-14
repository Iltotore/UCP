package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.CommandContext
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.ParsingResult._

import scala.collection.mutable

/**
 * A CommandElement is a part of the parsing process. It completes the passed CommandContext using the given CommandArguments.
 *
 * @tparam S the sender type.
 */
trait CommandElement[S] {

  /**
   * Complete the passed CommandContext using the given CommandArguments.
   *
   * @param sender    the command sender.
   * @param arguments the arguments used to parse the command.
   * @param context   the unfinished CommandContext to complete.
   * @return a ParsingResult[S] containing the finished context.
   */
  def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S]
}

object CommandElement {

  /**
   * Represent a CommandElement containing a key.
   *
   * @param key the unique key identifying this element.
   * @tparam S the sender type.
   */
  abstract class NamedElement[S](key: String) extends CommandElement[S] {

    private var required: Boolean = true

    /**
     * Get the element's unique key.
     *
     * @return the element's key.
     */
    def getKey: String = key

    /**
     * Test if this element is required or optional.
     *
     * @return true if required, false otherwise.
     */
    def isRequired: Boolean = required

    /**
     * Set this element as optional.
     *
     * @return this element for chaining.
     */
    def optional: NamedElement[S] = {
      required = false
      this
    }

  }

  /**
   * A sequance element dispatch arguments to its children elements.
   *
   * @param element the root CommandElement.
   * @tparam S the sender type.
   */
  implicit class SequenceElement[S](element: CommandElement[S]) extends CommandElement[S] {

    private val elements: mutable.Buffer[CommandElement[S]] = mutable.ArrayBuffer()

    {
      elements.addOne(element)
    }

    /**
     * Add a new CommandElement to this SequenceElement.
     *
     * @param element the element to be added.
     * @return this SequenceElement for chaining.
     */
    def and(element: CommandElement[S]): SequenceElement[S] = {
      elements.addOne(element)
      this
    }

    override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] = {
      for (element <- elements) {
        val result = element.parse(sender, arguments, context)
        if (result.getResultType != SUCCESS) return result
      }
      SUCCESS parsing arguments in context
    }
  }
}