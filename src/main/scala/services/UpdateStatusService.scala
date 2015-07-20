package services

import repositories.DefaultInterpreter._

object UpdateStatusService {
  def updateStatusIfExist(screenName: String, status: String): Twitter[String] =
    condition(
      existUserWithScreenName(pure(screenName)),
      updateStatus(pure(status)),
      pure("ng")
    )
}
