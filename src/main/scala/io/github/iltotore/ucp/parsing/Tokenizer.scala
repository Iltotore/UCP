package io.github.iltotore.ucp.parsing

import io.github.iltotore.ucp.util.Pollable

import scala.collection.mutable

trait Tokenizer {

  def tokenize(line: String): CommandLine
}

object Tokenizer {

  class Regex(splitter: String) extends Tokenizer {

    override def tokenize(line: String): CommandLine = {
      var words = line.split(splitter)
      val cmdId = words.head
      words = words.drop(1)
      val unknownTerms = mutable.ArrayBuffer.empty[Term]
      val namedTerms = mutable.ArrayBuffer.empty[Term.Named]
      val name = Pollable.empty[String]
      for (word <- words) {
        if (word.startsWith("--")) {
          if(name.isDefined) namedTerms += Term.Named(name.poll)
          name.put(word.substring(2))
        } else if (name.isDefined) namedTerms += Term.Named(name.poll, word)
        else unknownTerms += Term.Unknown(word)
      }
      CommandLine(cmdId, new TermGroup(unknownTerms.toSeq, namedTerms.toSeq))
    }
  }

}