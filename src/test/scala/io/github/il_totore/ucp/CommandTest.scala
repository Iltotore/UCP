package io.github.il_totore.ucp

import io.github.il_totore.ucp.CommandSpec.ImplicitSpec
import io.github.il_totore.ucp.GeneralResult._
import io.github.il_totore.ucp.parsing.BranchElement._
import io.github.il_totore.ucp.parsing.CommandElement.SequenceElement
import io.github.il_totore.ucp.parsing.EndElement._
import io.github.il_totore.ucp.parsing.NodeElement._
import io.github.il_totore.ucp.parsing.{BranchElement, CommandElement, NodeElement, ParsingResult}
import io.github.il_totore.ucp.registration.{CommandRegistry, PrefixedCommandRegistry}
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable.ListBuffer

class CommandTest extends AnyFlatSpec {


  def commandTest(): CommandSpec[String] = {
    def dummyExecutor(sender: String, context: CommandContext[String]): GeneralResult = {
      if (!context.getFirst[Boolean]("boolArg").getOrElse(false)) return FAILURE whilst "asserting true"
      SUCCESS whilst context.getFirst[Int]("time").get.toString
    }

    val aPermission: String => Boolean = name => name.equals("Il_totore") //Only for Il_totore <3
    val firstArg: CommandElement[String] = "boolArg" casting(_.toBoolean)
    val dayBranch: BranchElement[String] = label("day") of("time" casting(_.toInt))
    val weekBranch: BranchElement[String] = label("week") of("time" casting(_.toInt * 7))
    val secondArg: NodeElement[String] = "unit" choosing dayBranch or weekBranch
    "myCommand" describedAs "A test command" withPermission aPermission executing dummyExecutor requiring(firstArg and secondArg)
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
    val registry: CommandRegistry[String] = createRegister(commandTest())
    assert(executeTest(registry, "Il_totore", "/myCommand false").equals(FAILURE whilst "asserting true"))
    assert(
      executeTest(registry, "Il_totore", "/myCommand true week 1")
        .equals(executeTest(registry, "Il_totore", "/myCommand true day 7"))
    )
  }
}