package fr.il_totore.ucp

trait CommandRegistry {

  def parse(command: String): CommandContext
}
