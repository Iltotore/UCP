package io.github.iltotore.ucp

import io.github.iltotore.ucp.parsing.{CommandData, CommandLine, ParsingException, Tokenizer}

import scala.collection.mutable
import scala.util.{Failure, Try}

/**
  * A way to easily store and parse commands.
  * @tparam C the type of the CommandContext.
  * @tparam R the result type returned by this Command. No type restriction.
  */
trait CommandRegistry[C <: CommandContext, R] {

  /**
    * The registered commands.
    * @return A Seq containing all registered commands.
    */
  def commands: Seq[Command[C, R]]

  /**
    * Register the given command.
    * @param command the command to add to this registry.
    */
  def register(command: Command[C, R]): Unit

  /**
    * Unregister the given command.
    * @param command the command to remove from this registry.
    */
  def unregister(command: Command[C, R]): Unit

  /**
    * Alias for register.
    * @param command the command to add to this registry.
    */
  def +=(command: Command[C, R]): Unit = register(command)

  /**
    * Alias for unregister.
    * @param command the command to remove from this registry.
    */
  def -=(command: Command[C, R]): Unit = unregister(command)

  /**
    * Get the command from the given id.
    * @param id the command id. Can be the main name or an alias.
    * @return an Option containing the Command[C, R] if exists.
    */
  def apply(id: String): Option[Command[C, R]] = commands.find(_.name.possibilities.contains(id))

  /**
    * Try to parse the given CommandData using the given context.
    * @param context the CommandContext instance used to parse data.
    * @param data the CommandData containing the Command and the passed arguments.
    * @return an instance of Try[R] eventually containing the execution result.
    */
  def parse(context: C, data: CommandData[C, R]): Try[R] = Try {
    data.command.root.parse(context, data.terms)
    data.command.executor(context)
  }

  /**
    * Try to parse the given CommandData using the given context.
    * @param context the CommandContext instance used to parse data.
    * @param line the CommandLine parsed to CommandData, then used to fill the context.
    * @return an instance of Try[R] eventually containing the execution result.
    */
  def parseLine(context: C, line: CommandLine): Try[R] = apply(line.id)
    .map(cmd => (context, CommandData(cmd, line.terms)))
    .map(tuple => parse(tuple._1, tuple._2))
    .getOrElse(Failure(ParsingException.UnknownCommand(line.id)))

  /**
    * Try to parse the given CommandData using the given context.
    * @param context the CommandContext instance used to parse data.
    * @param line the raw String line to be parsed.
    * @param tokenizer the Tokenizer instance used to parse the raw line to CommandLine.
    * @return an instance of Try[R] eventually containing the execution result.
    */
  def parseString(context: C, line: String)(implicit tokenizer: Tokenizer): Try[R] = parseLine(context, tokenizer.tokenize(line))
}

object CommandRegistry {

  /**
    * An implementation of CommandRegistry supporting command prefix.
    * @param prefix the command prefix.
    * @tparam C the type of the CommandContext.
    * @tparam R the result type returned by this Command. No type restriction.
    */
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