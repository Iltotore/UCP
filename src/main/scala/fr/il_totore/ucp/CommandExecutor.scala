package fr.il_totore.ucp

trait CommandExecutor[S] {

  def execute(sender: S, commandData: CommandContext): CommandResult
}
