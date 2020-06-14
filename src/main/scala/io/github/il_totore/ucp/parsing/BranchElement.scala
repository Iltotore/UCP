package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.CommandContext
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.ParsingResult._

/**
 * A branch element is a bridge between a node child and his parent.
 *
 * @tparam S the sender type.
 */
abstract class BranchElement[S] extends CommandElement[S] {

  protected var child: Option[CommandElement[S]] = Option.empty

  /**
   * Used by the node to select the child.
   *
   * @param sender    the command sender as S.
   * @param arguments the command arguments parsed by an InputTokenizer.
   * @param context   the command context.
   * @return true if the branch is considered as valid.
   */
  def isValid(sender: S, arguments: CommandArguments, context: CommandContext[S]): Boolean

  /**
   * Get the element child.
   *
   * @return the node's child prefixed by this BranchElement.
   */
  def getChild: Option[CommandElement[S]] = child

  /**
   * Add the given CommandElement as this branch's child.
   *
   * @param child the future branch child.
   * @return this BranchElement for chaining.
   */
  def of(child: CommandElement[S]): BranchElement[S] = {
    this.child = Option(child)
    this
  }

  override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] =
    child.map(element => element.parse(sender, arguments, context)).getOrElse(FAILURE parsing arguments in context)

}

object BranchElement {

  /**
   * A BranchElement implementation which is considered as valid if the child can successfully parse the given arguments.
   *
   * @tparam S the sender type.
   */
  class UntaggableBranchElement[S] extends BranchElement[S] {

    override def isValid(sender: S, arguments: CommandArguments, context: CommandContext[S]): Boolean = {
      val index = arguments.getCurrentIndex
      val result = parse(sender, arguments, context).getResultType.isSuccessful
      arguments.reset(index)
      result
    }

  }

  /**
   * A BranchElement implementation evaluating a String prefix. If the given String predicate (default: equalsIgnoreCase(key)) returns true, this branch is considered as valid.
   *
   * @param key the key used to validate the prefix.
   * @tparam S the sender type.
   */
  class LabelledBranchElement[S](key: String) extends BranchElement[S] {

    private var stringValidator: (String, String) => Boolean = (first, second) => first.equalsIgnoreCase(second)

    override def isValid(sender: S, arguments: CommandArguments, context: CommandContext[S]): Boolean = {
      val valid = stringValidator(key, arguments.next.orNull)
      if (!valid) arguments.back(1)
      valid
    }

    /**
     * Set the stringValidator. Default: String#equalsIgnoreCase(key)
     *
     * @param stringValidator the new stringValidator.
     * @return this LabelledBranchElement for chaining.
     */
    def using(stringValidator: (String, String) => Boolean): LabelledBranchElement[S] = {
      this.stringValidator = stringValidator
      this
    }

  }

  /**
   * A simple factory method to make the declaration syntaxe more readable:
   * <pre>
   * label ("aKey") of (myElement)
   * </pre>
   *
   * @param key the key passed to the LabelledBranchElement constructor.
   * @tparam S the sender type.
   * @return the newly created LabelledBranchElement.
   */
  def label[S](key: String): LabelledBranchElement[S] = new LabelledBranchElement(key)

}
