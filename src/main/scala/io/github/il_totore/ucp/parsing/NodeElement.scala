package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.CommandContext
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.CommandElement.NamedElement
import io.github.il_totore.ucp.parsing.ParsingResult._

import scala.collection.mutable.ListBuffer

/**
 * A NodeElement groups multiple branches.
 *
 * @param key the unique key identifying this element.
 * @tparam S the sender type.
 */
class NodeElement[S](key: String) extends NamedElement[S](key: String) {

  protected val branches: ListBuffer[BranchElement[S]] = ListBuffer()

  override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] = {
    for (branch <- branches) {
      if (branch.isValid(sender, arguments, context)) {
        return branch.parse(sender, arguments, context)
      }
    }
    FAILURE parsing arguments in context
  }
}

object NodeElement {

  /**
   * An implicit implementation of NodeElement.
   *
   * @param key the unique key identifying this element.
   * @tparam S the sender type.
   */
  implicit class ImplicitNodeElement[S](key: String) extends NodeElement[S](key) {

    /**
     * Add the given BranchElement to this node.
     *
     * @param element the element to attach.
     * @return this ImplicitNodeElement for chaining.
     */
    def choosing(element: BranchElement[S]): ImplicitNodeElement[S] = {
      branches.addOne(element)
      this
    }

    /**
     * A synonymous of ImplicitNodeElement#choosing, making the command creation syntax clearer.
     *
     * @param element the element to attach.
     * @return this ImplicitNodeElement for chaining.
     */
    def or(element: BranchElement[S]): ImplicitNodeElement[S] = choosing(element)
  }

}
