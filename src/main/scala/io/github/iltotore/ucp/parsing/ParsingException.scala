package io.github.iltotore.ucp.parsing

class ParsingException(msg: String) extends IllegalArgumentException(msg)

object ParsingException {

  case class UnknownCommand(name: String) extends ParsingException(name)

  case class InvalidArgument(name: String, value: String) extends ParsingException(s"for $name=$value")

  case class MissingArgument(name: String) extends ParsingException(name)
}