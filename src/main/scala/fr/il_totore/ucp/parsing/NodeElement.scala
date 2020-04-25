package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext
import fr.il_totore.ucp.GeneralResult._
import fr.il_totore.ucp.parsing.CommandElement.NamedElement
import fr.il_totore.ucp.parsing.ParsingResult._

import scala.collection.mutable.ListBuffer

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

  override def getUsage(sender: S): String = ""
}

object NodeElement {

  implicit class ImplicitNodeElement[S](key: String) extends NodeElement[S](key) {

    def choosing(element: BranchElement[S]): ImplicitNodeElement[S] = {
      branches.addOne(element)
      this
    }

    def or(element: BranchElement[S]): ImplicitNodeElement[S] = choosing(element)
  }

}