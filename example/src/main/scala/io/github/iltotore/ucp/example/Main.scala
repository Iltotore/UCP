package io.github.iltotore.ucp.example

import io.github.iltotore.ucp.parsing.Tokenizer
import io.github.iltotore.ucp.parsing.param.{GenericParam, MiscParam}
import io.github.iltotore.ucp.{Command, CommandContext, CommandName, CommandRegistry}

import scala.collection.mutable
import scala.concurrent.duration.TimeUnit
import scala.io.StdIn
import scala.util.{Failure, Success}

object Main {

  def main(args: Array[String]): Unit = {

    implicit val tokenizer: Tokenizer = new Tokenizer.Regex(" ")

    val registry = new CommandRegistry.Prefixed[CommandContext.Mapped, String]("/")
    registry += Command(
      name = CommandName("time"),
      executor = CommandTimeExecutor,
      root = MiscParam.sequence(
        GenericParam.long("time"),
        MiscParam.optional(GenericParam.javaEnum[TimeUnit]("unit"))
      )
    )


    var line = ""
    while (!line.equals("stop")){
      if(!line.isEmpty) registry.parseString(new CommandContext.Mapped(mutable.Map.empty), line) match {
        case Success(value) => println(s"result: $value")

        case Failure(exception) => exception.printStackTrace()
      }
      line = Option(StdIn.readLine()).getOrElse("")
    }
  }
}
