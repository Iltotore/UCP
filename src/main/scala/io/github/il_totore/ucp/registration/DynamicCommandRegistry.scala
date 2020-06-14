package io.github.il_totore.ucp.registration

import io.github.il_totore.ucp.CommandSpec

import scala.collection.mutable

/**
 * A partial CommandRegistry implementation using a Buffer.
 *
 * @param buffer the Buffer instance used to store registered specs.
 * @tparam S the sender type.
 */
abstract class DynamicCommandRegistry[S](buffer: mutable.Buffer[CommandSpec[S]]) extends CommandRegistry[S] {

  override def register(spec: CommandSpec[S]): Unit = buffer += spec

  override def unregister(spec: CommandSpec[S]): Unit = buffer -= spec

  override def unregisterIf(predicate: CommandSpec[S] => Boolean): Unit = buffer.filter(predicate).foreach(buffer.-=)
}
