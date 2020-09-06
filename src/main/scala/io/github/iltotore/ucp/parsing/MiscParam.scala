package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.CommandContext
import io.github.iltotore.ucp.parsing.Param.Identifiable

object MiscParam {

  class Sequence(params: Param*) extends Param {
    override def parse(context: CommandContext, terms: TermGroup): Unit = {
      for(i <- params.indices) params(i).parse(context, terms)
    }
  }

  class Optional(param: Identifiable) extends Param {
    override def parse(context: CommandContext, terms: TermGroup): Unit = {
      if(param.isValid(context, terms)) param.parse(context, terms)
    }
  }

  object Empty extends Param {
    override def parse(context: CommandContext, terms: TermGroup): Unit = {}
  }

  class Contains[T](_key: String, seq: Seq[T], fun: (Term, T) => Boolean) extends Param.Named {
    override def key: String = _key

    override def parse(context: CommandContext, terms: TermGroup): Unit = terms.nextOption(_key)
      .filter(term => seq.exists(fun(term, _)))
      .orElse{throw ParsingException.MissingArgument(_key)}
      .foreach(context.putArgument(_key, _))
  }

  class Keyword(_key: String, seq: Seq[String], fun: (String, String) => Boolean = _ equals _)
    extends Contains[String](_key, seq, (term, value) => fun(term.value, value))
}
