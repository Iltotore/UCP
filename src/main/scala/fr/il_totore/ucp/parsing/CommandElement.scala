package fr.il_totore.ucp.parsing

import fr.il_totore.ucp.CommandContext

abstract class CommandElement[S](key: String, required: Boolean) {

  def getKey: String = key
  def isOptional: Boolean = required

  def parse(sender: S, arguments: CommandArguments, context: CommandContext): Unit = {
    val value: Object = parseValue(sender, arguments)
    val key = getKey
    if (key != null && value != null) value match {
      case iterable: Iterable[_] =>
        for (ent <- iterable) {
          context.putArgument(key, ent)
        }
      case _ => context.putArgument(key, value)
    }
  }

  def canParse(sender: S, arguments: CommandArguments): Boolean

  def parseValue(sender: S, arguments: CommandArguments): Object

  def getUsage(sender: S): String = if (required) "<" + key + ">" else "[" + key + "]"
}
