package io.github.iltotore.ucp.parsing

/**
  * Represent a single command term containing a String value
  */
trait Term {
  def value: String
}


object Term {

  case class Unknown(value: String) extends Term

  case class Named(name: String, value: String = "") extends Term
}