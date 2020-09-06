package io.github.iltotore.ucp

case class CommandName(name: String, aliases: String*) {

  def possibilities: Seq[String] = aliases :+ name
}
