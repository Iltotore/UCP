package fr.il_totore.ucp.exception

class ArgumentParseException(message: String, command: String) extends CommandException(message + " Near") {


  def this(message: String, command: String, position: Int) = {
    this("")
  }
}
