package io.github.il_totore.ucp

import io.github.il_totore.ucp.GeneralResult.ResultType

trait GeneralResult {

  def getResultType: ResultType

  def getMessage: Option[String]

  override def equals(obj: Any): Boolean = {
    if (!obj.isInstanceOf[GeneralResult]) return false
    obj.asInstanceOf[GeneralResult].getMessage.equals(getMessage) && obj.asInstanceOf[GeneralResult].getResultType.equals(getResultType)
  }
}

object GeneralResult {

  case class ResultType(code: Int, successful: Boolean) {

    def getCode: Int = code

    def isSuccessful: Boolean = successful

  }

  val SUCCESS: ResultType = ResultType(0, true)
  val FAILURE: ResultType = ResultType(-1, false)

  implicit class ImplicitResult(resultType: ResultType) extends GeneralResult {

    var message: Option[String] = Option.empty

    def whilst(message: String): ImplicitResult = {
      this.message = Option(message)
      this
    }

    override def getResultType: ResultType = resultType

    override def getMessage: Option[String] = message

  }

}
