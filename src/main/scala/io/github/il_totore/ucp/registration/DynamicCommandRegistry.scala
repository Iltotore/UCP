package io.github.il_totore.ucp.registration

import io.github.il_totore.ucp.CommandSpec

import scala.collection.mutable.ListBuffer

abstract class DynamicCommandRegistry[S](sequence: ListBuffer[CommandSpec[S]]) extends CommandRegistry[S] {

  override def register(spec: CommandSpec[S]): Unit = sequence += spec

  override def unregister(spec: CommandSpec[S]): Unit = sequence -= spec

  override def filter(predicate: CommandSpec[S] => Boolean): Unit = sequence.filterInPlace(predicate)
}
