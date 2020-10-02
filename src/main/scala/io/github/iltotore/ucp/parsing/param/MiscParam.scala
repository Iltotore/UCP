package io.github.iltotore.ucp.parsing.param

import io.github.iltotore.ucp.CommandContext
import io.github.iltotore.ucp.parsing.param.Param.Identifiable
import io.github.iltotore.ucp.parsing.{ParsingException, Term, TermGroup}
import io.github.iltotore.ucp.util.OptionOps

object MiscParam {

  class Sequence(params: Seq[Param]) extends Param {
    override def parse(context: CommandContext, terms: TermGroup): Unit = params.foreach(_.parse(context, terms))
  }


  class Optional(param: Identifiable) extends Param {

    override def parse(context: CommandContext, terms: TermGroup): Unit = param.validate(context, terms)
      .fallback(param.parse(context, terms))
  }

  class Contains[T](_key: String, seq: Seq[T], fun: (Term, T) => Boolean) extends Param.Named {
    override def key: String = _key

    override def parse(context: CommandContext, terms: TermGroup): Unit = terms.nextOption(_key)
      .flatMap(term => seq.find(fun(term, _)))
      .orElse {
        throw ParsingException.MissingArgument(_key)
      }
      .foreach(context.putArgument(_key, _))
  }

  class Keyword(_key: String, seq: Seq[String], fun: (String, String) => Boolean = _ equals _)
    extends Contains[String](_key, seq, (term, value) => fun(term.value, value))

  /**
   * Represent an empty parameter, doing nothing. Used as default root parameter in Command.
   */
  def empty: Param = (_: CommandContext, _: TermGroup) => {}

  /**
   * A composed parameter parsing (in order) its children, allowing multiple-parameters commands.
   *
   * @param params the child sequence.
   */
  def sequence(params: Param*): Sequence = new Sequence(params)

  /**
   * An optional representation of the given Param.Identifiable.
   * @param param the parameter to be turned as optional.
   */
  def optional(param: Identifiable): Optional = new Optional(param)

  /**
   * Represent a parameter choosing between multiple possibilities
   * @param key the key used to identify this parameter.
   * @param seq a Seq[T] containing the multiple possibilities.
   * @param equalizer the predicate used to pick the right possibility.
   * @tparam T the output and possibilities type.
   */
  def elementOf[T](key: String)(seq: Seq[T], equalizer: (Term, T) => Boolean): Contains[T] = new Contains(key, seq, equalizer)

  /**
   * A MiscParam.Contains implementation choosing between keywords.
   * @param key the key used to identify this parameter.
   * @param seq a Seq[String] containing the multiple keywords.
   */
  def keyword(key: String)(seq: Seq[String]): Contains[String] = elementOf(key)(seq, (term, word) => term.value.equalsIgnoreCase(word))
}
