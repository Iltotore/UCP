package fr.il_totore.ucp

trait CommandResult {

  def getResultType: Int

  def getMessage: Option[String]
}


object CommandResult {

  implicit class ImplicitResult(resultType: Int) extends CommandResult {

    private var message: Option[String] = Option.empty

    def whilst(message: String): CommandResult = {
      this.message = Option(message)
      this
    }

    def withoutMessage(): CommandResult = {
      this
    }

    override def getResultType: Int = resultType

    override def getMessage: Option[String] = message
  }

  /**
   * Generic success code
   */
  val SUCCESS: Int = 0

  /**
   * Generic failure code
   */
  val FAILURE: Int = -1
}