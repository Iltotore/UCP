package io.github.iltotore.ucp.parsing

/**
  * Represent a command id and the passed parameters.
  * @param id the Command id, used to retrieve the Command instance.
  * @param terms the semi-parsed parameters.
  */
case class CommandLine(id: String, terms: TermGroup)