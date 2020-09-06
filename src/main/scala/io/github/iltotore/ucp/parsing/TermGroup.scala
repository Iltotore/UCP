package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.util.Fun

class TermGroup(val unknowns: Seq[Term], val named: Seq[Term.Named]) {

  var nextIndex: Int = 0

  def peek(name: String): Option[Term] =
    named.find(_.name equals name)
      .orElse(Option.when(unknowns.indices.contains(nextIndex))(unknowns(nextIndex)))

  def nextOption(name: String): Option[Term] =
    peek(name)
    .map(Fun.sideMatch[Term, Term.Named](orElse = _ => nextIndex+=1))

  def next(name: String): Term = nextOption(name).get
}
