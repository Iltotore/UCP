package fr.il_totore.ucp.parsing

class LambdaCommandElement[S](key: String, required: Boolean, valueParser: (S, CommandArguments) => AnyRef, parsePredicate: (S, CommandArguments) => Boolean) extends CommandElement[S](key, required) {
  override def canParse(sender: S, arguments: CommandArguments): Boolean = parsePredicate.apply(sender, arguments)

  override def parseValue(sender: S, arguments: CommandArguments): AnyRef = valueParser.apply(sender, arguments)
}
