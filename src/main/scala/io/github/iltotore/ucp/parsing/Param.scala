package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.CommandContext
import io.github.iltotore.ucp.util.OptionOps

/**
 * Represent a command parameter.
 */
trait Param {

  /**
   * Parse this parameter using the passed terms.
   *
   * @param context the context to enrich.
   * @param terms   the TermGroup to parse with.
   */
  def parse(context: CommandContext, terms: TermGroup): Unit
}



object Param {


  /**
   * A validity-checkable parameter.
   */
  trait Identifiable extends Param {

    /**
     * Validate the parsability of the current context and terms.
     *
     * @param context the context to enrich.
     * @param terms   the TermGroup to parse with.
     * @return true if this parameter can be parsed using the given context and terms.
     */
    def validate(context: CommandContext, terms: TermGroup): Option[_ <: ParsingException]

    override def parse(context: CommandContext, terms: TermGroup): Unit =
      validate(context, terms).foreach(throw _)
  }

  /**
   * A keyed parameter.
   */
  trait Named extends Identifiable {

    def key: String

    override def validate(context: CommandContext, terms: TermGroup): Option[_ <: ParsingException] = terms.peek(key)
      .fallback(ParsingException.MissingArgument(key))
  }

  /**
   * A chainable parsing parameter.
   *
   * @tparam I the input type.
   * @tparam O the output type.
   */
  trait Flow[I, O] extends Param {
    def parseValue(input: I): O

    def map[T](fun: O => T): Flow[I, T]
    def flatMap[T](flow: Flow[O, T]): Flow[I, T] = map(flow.parseValue)
  }

  /**
   * A basic keyed parameter parsing a Term to a value added to the given context.
   *
   * @param _key the key used to identify this parameter.
   * @tparam T the output type.
   */
  abstract class Single[T](_key: String) extends Flow[Term, T] with Named {

    override def parse(context: CommandContext, terms: TermGroup): Unit = {
      super.parse(context, terms)
      val term = terms.next(key)
      val value = try parseValue(term) catch {
        case parsingException: ParsingException => throw parsingException

        case other: Exception => throw ParsingException.InvalidArgument(key, term.value, other)
      }
      context.putArgument(key, value)
    }

    override def key: String = _key

    override def map[U](fun: T => U): Flow[Term, U] = new Single[U](_key) {
      override def parseValue(input: Term): U = fun(Single.this.parseValue(input))
    }
  }
}