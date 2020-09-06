package io.github.iltotore.ucp

import io.github.iltotore.ucp.parsing.{CommandData, CommandLine, ParsingException, Tokenizer}

import scala.collection.mutable
import scala.util.{Failure, Try}

trait CommandRegistry[C <: CommandContext, R] {

  def commands: Seq[Command[C, R]]

  def register(command: Command[C, R]): Unit

  def unregister(command: Command[C, R]): Unit

  def +=(command: Command[C, R]): Unit = register(command)

  def -=(command: Command[C, R]): Unit = unregister(command)

  def apply(id: String): Option[Command[C, R]] = commands.find(_.name.possibilities.contains(id))

  def parse(context: C, data: CommandData[C, R]): Try[R] = Try {
    data.command.root.parse(context, data.terms)
    data.command.executor(context)
  }

  def parseLine(context: C, line: CommandLine): Try[R] = apply(line.id)
    .map(cmd => (context, CommandData(cmd, line.terms)))
    .map(tuple => parse(tuple._1, tuple._2))
    .getOrElse(Failure(ParsingException.UnknownCommand(line.id)))

  def parseString(context: C, line: String)(implicit tokenizer: Tokenizer): Try[R] = parseLine(context, tokenizer.tokenize(line))
}

object CommandRegistry {

  class Prefixed[C <: CommandContext, R](prefix: String) extends CommandRegistry[C, R] {

    private val buffer = mutable.ArrayBuffer.empty[Command[C, R]]

    override def commands: Seq[Command[C, R]] = buffer.toSeq

    override def register(command: Command[C, R]): Unit = buffer += command

    override def unregister(command: Command[C, R]): Unit = buffer -= command

    override def parseString(context: C, line: String)(implicit tokenizer: Tokenizer): Try[R] = {
      if(line.startsWith(prefix)) super.parseString(context, line.substring(prefix.length))
      else Failure(new ParsingException(s"Prefix $prefix not found"))
    }
  }

}