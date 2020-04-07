package fr.il_totore.ucp

import fr.il_totore.ucp.CommandResult._
import fr.il_totore.ucp.CommandSpec.ImplicitSpec
import org.scalatest.flatspec.AnyFlatSpec

class CommandTest extends AnyFlatSpec {


  "A CommandSpec" should "be consistent" in {
    val aPermission: String => Boolean = name => name.equals("Il_totore")
    val aCommandExecutor: (String, CommandContext) => CommandResult = (_: String, _: CommandContext) => null

    val myCommand = "myCommand" describedAs "A test command" withPermission aPermission executing aCommandExecutor
  }

  "A CommandResult" should "be consistent" in {
    val result = SUCCESS whilst "creating a result"
  }
}