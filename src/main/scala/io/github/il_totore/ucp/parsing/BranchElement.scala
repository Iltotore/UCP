package io.github.il_totore.ucp.parsing

import io.github.il_totore.ucp.CommandContext
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.ParsingResult._

abstract class BranchElement[S] extends CommandElement[S] {

  protected var child: Option[CommandElement[S]] = Option.empty

  def isValid(sender: S, arguments: CommandArguments, context: CommandContext[S]): Boolean

  def getChild: Option[CommandElement[S]] = child

  def of(child: CommandElement[S]): BranchElement[S] = {
    this.child = Option(child)
    this
  }

  override def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S] =
    child.map(element => element.parse(sender, arguments, context)).getOrElse(FAILURE parsing arguments in context)

}

object BranchElement {

  class UntaggableBranchElement[S] extends BranchElement[S] {

    override def isValid(sender: S, arguments: CommandArguments, context: CommandContext[S]): Boolean = {
      val index = arguments.getCurrentIndex
      val result = parse(sender, arguments, context).getResultType.isSuccessful
      arguments.reset(index)
      result
    }

  }

  class LabelledBranchElement[S](key: String) extends BranchElement[S] {

    private var stringValidator: (String, String) => Boolean = (first, second) => first.equalsIgnoreCase(second)

    override def isValid(sender: S, arguments: CommandArguments, context: CommandContext[S]): Boolean = {
      val valid = stringValidator(key, arguments.next.orNull)
      if (!valid) arguments.back(1)
      valid
    }

    def using(stringValidator: (String, String) => Boolean): LabelledBranchElement[S] = {
      this.stringValidator = stringValidator
      this
    }

  }

  def label[S](key: String): LabelledBranchElement[S] = new LabelledBranchElement(key)

}
