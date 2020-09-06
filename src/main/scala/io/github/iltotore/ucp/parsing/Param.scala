package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.CommandContext

trait Param {

  def parse(context: CommandContext, terms: TermGroup): Unit
}



object Param {

  trait Identifiable extends Param {
    def isValid(context: CommandContext, terms: TermGroup): Boolean
  }

  trait Named extends Identifiable {

    def key: String

    override def isValid(context: CommandContext, terms: TermGroup): Boolean = terms.peek(key).isDefined
  }

  trait Flow[I, O] extends Param {
    def parseValue(input: I): O

    def map[T](fun: O => T): Flow[I, T]
    def flatMap[T](flow: Flow[O, T]): Flow[I, T] = map(flow.parseValue)
  }

  abstract class Single[T](_key: String) extends Flow[Term, T] with Named {

    override def parse(context: CommandContext, terms: TermGroup): Unit =
      context.putArgument(key, parseValue(terms.next(key)))

    override def key: String = _key

    override def map[U](fun: T => U): Flow[Term, U] = new Single[U](_key) {
      override def parseValue(input: Term): U = fun(Single.this.parseValue(input))
    }
  }
}

// /give STONE --data 1 -p Il_totore --amount 1
// /give STONE (data) Il_totore
// /give STONE Il_totore