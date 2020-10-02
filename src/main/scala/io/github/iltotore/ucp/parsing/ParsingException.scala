package io.github.iltotore.ucp.parsing

/**
  * Represent an IllegalArgumentException thrown while parsing.
  * @param msg the error message.
  */
class ParsingException(msg: String) extends IllegalArgumentException(msg)

object ParsingException {

  /**
    * Thrown when parsing an unknown command.
    * @param name the passed (and wrong) command id.
    */
  case class UnknownCommand(name: String) extends ParsingException(name)

  /**
    * Thrown when parsing an invalid argument, especially when Identifiable#isValid returned false.
    * @param name the parameter name.
    * @param value the value passed to this parameter.
    */
  case class InvalidArgument(name: String, value: String) extends ParsingException(s"for $name=$value")

  /**
    * Thrown when a required argument is not passed.
    * @param name the parameter name.
    */
  case class MissingArgument(name: String) extends ParsingException(name)
}