package fr.il_totore.ucp

import fr.il_totore.ucp.CommandSpec.ImplicitSpec
import fr.il_totore.ucp.GeneralResult._
import fr.il_totore.ucp.parsing.BranchElement._
import fr.il_totore.ucp.parsing.CommandElement.SequenceElement
import fr.il_totore.ucp.parsing.EndElement._
import fr.il_totore.ucp.parsing.NodeElement._
import fr.il_totore.ucp.parsing.{BranchElement, CommandElement, NodeElement, ParsingResult}
import fr.il_totore.ucp.registration.{CommandRegistry, PrefixedCommandRegistry}
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable.ListBuffer

class CommandTest extends AnyFlatSpec {


  def commandTest(): CommandSpec[String] = {
    def dummyExecutor(sender: String, context: CommandContext[String]): GeneralResult = {
      if (!context.getFirst[Boolean]("boolArg").getOrElse(false)) return FAILURE whilst "asserting true"
      SUCCESS whilst Math.pow(context.getFirst[Int]("intArg").get.toDouble, 2).toString
    }

    val aPermission: String => Boolean = name => name.equals("Il_totore") //Only for Il_totore <3
    val firstArg: CommandElement[String] = "boolArg" casting(_.toBoolean)
    val secondArg: CommandElement[String] = "intArg" casting(_.toInt) orElse 0
    val dayBranch: BranchElement[String] = label("day") of("dayArg" casting(_.toInt))
    val weekBranch: BranchElement[String] = label("week") of("weekArg" casting(_.toInt * 7))
    val thirdArg: NodeElement[String] = "unit" choosing dayBranch or weekBranch
    "myCommand" describedAs "A test command" withPermission aPermission executing dummyExecutor requiring(firstArg and secondArg and thirdArg)
  }

  def createRegister(spec: CommandSpec[String]): CommandRegistry[String] = {
    val registry: CommandRegistry[String] = new PrefixedCommandRegistry(ListBuffer(), "/")
    registry.register(spec)
    registry
  }

  def executeTest(registry: CommandRegistry[String], sender: String, cmd: String): GeneralResult = {
    val result: ParsingResult[String] = registry.parse(sender, cmd)
    assert(result.getContext.isDefined)
    result.getContext.get.execute(sender)
  }


  "A CommandRegistry" should "register and parse commands consistently" in {
    var result = executeTest(createRegister(commandTest()), "Il_totore", "/myCommand false")
    assert(result.equals(FAILURE whilst "asserting true"))
    result = executeTest(createRegister(commandTest()), "Il_totore", "/myCommand true 5")
    assert(result.equals(SUCCESS whilst "25.0"))
  }
}