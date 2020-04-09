package fr.il_totore.ucp

import fr.il_totore.ucp.CommandResult._
import fr.il_totore.ucp.CommandSpec.ImplicitSpec
import fr.il_totore.ucp.parsing.CommandElement._
import fr.il_totore.ucp.parsing.{CommandArguments, CommandElement, ParsingResult}
import fr.il_totore.ucp.registration.{CommandRegistry, PrefixedCommandRegistry}
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable.ListBuffer

class CommandTest extends AnyFlatSpec {


  def commandTest(): CommandSpec[String] = {
    val aPermission: String => Boolean = name => name.equals("Il_totore")
    val aCommandExecutor: (String, CommandContext[String]) => CommandResult = (sender: String, context: CommandContext[String]) => {
      assert(context.getFirst[Boolean]("boolArg").getOrElse(false))
      SUCCESS whilst "executing test command"
    }
    val element: CommandElement[String] = "boolArg" lambda((sender: String, args: CommandArguments, context: CommandContext[String]) => true)
    "myCommand" describedAs "A test command" withPermission aPermission executing aCommandExecutor withElement element
  }

  def createRegister(spec: CommandSpec[String]): CommandRegistry[String] = {
    val registry: CommandRegistry[String] = new PrefixedCommandRegistry(ListBuffer(), "/")
    registry.register(spec)
    registry
  }

  def executingTest(registry: CommandRegistry[String], sender: String, cmd: String): Unit = {
    val result: ParsingResult[String] = registry.parse(sender, cmd)
    assert(result.getContext.isDefined)
    result.getContext.get.execute(sender)
  }


  "A Command" should "be consistent" in commandTest()
  "A CommandRegistry" should "register and parse commands consistently" in executingTest(createRegister(commandTest()), "Il_totore", "/myCommand true")
}