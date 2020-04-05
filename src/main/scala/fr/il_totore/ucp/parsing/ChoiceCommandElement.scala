package fr.il_totore.ucp.parsing

class ChoiceCommandElement[S](key: String, required: Boolean, possibilities: List[CommandElement[S]]) extends CommandElement[S](key, required) {

  override def canParse(sender: S, arguments: CommandArguments): Boolean = {
    possibilities.exists(possibility => possibility.canParse(sender, arguments))
  }

  override def parseValue(sender: S, arguments: CommandArguments): AnyRef = {
    possibilities.find(possibility => possibility.canParse(sender, arguments))
      .get
      .parseValue(sender, arguments)
  }
}
