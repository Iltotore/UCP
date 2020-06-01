package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext
import fr.il_totore.ucp.GeneralResult._
import fr.il_totore.ucp.parsing.ParsingResult._

import scala.collection.mutable.ListBuffer

trait CommandElement[S] {

  def parse(sender: S, arguments: CommandArguments, context: CommandContext[S]): ParsingResult[S]
}

object CommandElement {

  abstract class NamedElement[S](key: String) extends CommandElement[S] {

    private var required: Boolean = true

    def getKey: String = key

    def isRequired: Boolean = required

    def optional: NamedElement[S] = {
      required = false
      this
    }

  }


  implicit class SequenceElement[S](element: CommandElement[S]) extends CommandElement[S] {

    private val elements: ListBuffer[CommandElement[S]] = ListBuffer()

    {
      elements.addOne(element)
    }

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