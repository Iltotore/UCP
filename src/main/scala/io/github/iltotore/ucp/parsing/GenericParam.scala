package io.github.iltotore.ucp.parsing

class GenericParam[T](key: String)(parser: String => T) extends Param.Single[T](key){

  override def parseValue(term: Term): T = parser(term.value)
}

object GenericParam {

  case class Boolean(_key: String) extends GenericParam[scala.Boolean](_key)(_.toBoolean)

  case class Byte(_key: String) extends GenericParam[scala.Byte](_key)(_.toByte)

  case class Short(_key: String) extends GenericParam[scala.Short](_key)(_.toShort)

  case class Integer(_key: String) extends GenericParam[scala.Int](_key)(_.toInt)

  case class Long(_key: String) extends GenericParam[scala.Long](_key)(_.toLong)
  
  case class Float(_key: String) extends GenericParam[scala.Float](_key)(_.toFloat)
  
  case class Double(_key: String) extends GenericParam[scala.Double](_key)(_.toDouble)
  
  case class Char(_key: String) extends GenericParam[scala.Char](_key)(_.head)
  
  case class Raw(_key: String) extends GenericParam[String](_key)(txt => txt)

  //TODO Dotty: add Enum param
}