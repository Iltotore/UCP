package io.github.iltotore.ucp

/**
  * A case class regrouping a name and its aliases.
  * @param name the main name of the command.
  * @param aliases the aliases of the name.
  */
case class CommandName(name: String, aliases: String*) {

  /**
    * Return the name and its aliases
    * @return a Seq[String] resulting in aliases :+ name
    */
  def possibilities: Seq[String] = aliases :+ name
}
