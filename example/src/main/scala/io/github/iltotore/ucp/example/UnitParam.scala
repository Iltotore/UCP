package io.github.iltotore.ucp.example

import java.util.concurrent.TimeUnit

import io.github.iltotore.ucp.CommandContext
import io.github.iltotore.ucp.parsing.{GenericParam, TermGroup}

import scala.concurrent.duration.TimeUnit

class UnitParam(key: String) extends GenericParam[TimeUnit](key)(TimeUnit.valueOf){

  override def isValid(context: CommandContext, terms: TermGroup): Boolean = {
    terms.peek(key).exists(term => TimeUnit.values().exists(_.name() equals term.value))
  }
}
