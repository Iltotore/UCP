package io.github.iltotore.ucp.parsing

import scala.reflect.ClassTag

/**
 * A simple Single parameter using the passed function to parse its term.
 *
 * @param key the key used to identify this parameter.
 * @param parser the function used to parse the term's value.
 * @tparam T the output type.
 */
class GenericParam[T](key: String)(parser: String => T) extends Param.Single[T](key){

  override def parseValue(term: Term): T = parser(term.value)
}

object GenericParam {

  def apply[T](key: String)(parser: String => T): GenericParam[T] = new GenericParam(key)(parser)

  def boolean(key: String): GenericParam[Boolean] = apply(key)(_.toBoolean)

  def byte(key: String): GenericParam[Byte] = apply(key)(_.toByte)

  def short(key: String): GenericParam[Short] = apply(key)(_.toShort)

  def int(key: String): GenericParam[Int] = apply(key)(_.toInt)

  def long(key: String): GenericParam[Long] = apply(key)(_.toLong)

  def float(key: String): GenericParam[Float] = apply(key)(_.toFloat)

  def double(key: String): GenericParam[Double] = apply(key)(_.toDouble)

  def raw(key: String): GenericParam[String] = apply(key)(t => t)


  //TODO Use Dotty's dependant types to improve generic
  def enumName[T](key: String)(enumeration: Enumeration): GenericParam[T] = apply(key)(enumeration.withName(_).asInstanceOf[T])

  def enumId[T](key: String)(enumeration: Enumeration): GenericParam[T] = apply(key)(txt => enumeration(txt.toInt).asInstanceOf[T])

  def javaEnum[T <: Enum[T] : ClassTag](key: String)(implicit tag: ClassTag[T]): GenericParam[T] = apply(key)(Enum.valueOf(tag.runtimeClass.asInstanceOf[Class[T]], _))
}