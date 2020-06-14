package io.github.il_totore.ucp

import io.github.il_totore.ucp.GeneralResult.ResultType

/**
 * A simple trait containing a ResultType and an optional message.
 */
trait GeneralResult {

  /**
   * Get the type of this result.
   *
   * @return the ResultType of this result.
   */
  def getResultType: ResultType

  /**
   * Get the optional message.
   *
   * @return an Option[String] containing the message if present.
   */
  def getMessage: Option[String]

  override def equals(obj: Any): Boolean = {
    if (!obj.isInstanceOf[GeneralResult]) return false
    obj.asInstanceOf[GeneralResult].getMessage.equals(getMessage) && obj.asInstanceOf[GeneralResult].getResultType.equals(getResultType)
  }
}

object GeneralResult {

  /**
   * A simple case class containing a result code and a boolean.
   *
   * @param code       the result code as Int
   * @param successful true if successful, false otherwise.
   */
  case class ResultType(code: Int, successful: Boolean) {

    def getCode: Int = code

    def isSuccessful: Boolean = successful

  }

  val SUCCESS: ResultType = ResultType(0, true)
  val FAILURE: ResultType = ResultType(-1, false)

  /**
   * An implicit implementation of GeneralResult.
   * Example:
   * <pre>
   * SUCCESS whilst "doing something"
   * </pre>
   *
   * @param resultType the result type used to instantiate implicitly the classe
   */
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
