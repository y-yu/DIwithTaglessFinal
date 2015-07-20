package services

import models.TwitterEnv
import utils.Reader._
import repositories.DefaultInterpreter._

import scala.concurrent.Future

/**
 * Created by hikaru_yoshimura on 2015/07/20.
 */
object UpdateStatusService {
  def updateStatusIfExist(screenName: String, status: String): Twitter[Future[String]] =
    condition(
      existUserWithScreenName(pure(screenName)),
      updateStatus(pure(status)),
      pure[TwitterEnv, Twitter[Future[String]]]({println("ng"); Future.successful("ng")})
    )
}
