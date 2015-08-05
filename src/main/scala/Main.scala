import models._
import play.api.libs.ws.WSResponse
import repositories.LetInSYMInterpreter._
import utils.Free

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration

//import services.UpdateStatusService._


object Main {
  import utils.Reader._
  import repositories.TwitterRepositoryDI._

  def main(args: Array[String]): Unit = {
    // (for {
    //   fb <- existUserWithScreenName("_yyu_")
    //   _  <- local(
    //           (e: UseWSClient with UseOAuthCred) =>
    //             if (Await.result(fb, Duration.Inf))
    //               DefaultEnvironment.adminEnvironment
    //             else
    //               e,
    //           updateStatus("test")
    //         )
    // } yield () ).run(DefaultEnvironment.defaultEnvironment)

    // import models.Twitter._
    // import repositories.TwitterInterpreter._

    // val dsl = fetch(
    //   "_yyu_",
    //   res =>
    //     if (res.status == 200)
    //       update("exist")
    //     else
    //       update("not exist")
    // )
    // runTwitter(dsl, DefaultEnvironment.defaultEnvironment)

    import repositories.TwitterSYMInterpreter._
    import repositories.DeleteSYMInterpreter._
    import repositories.LetInSYMInterpreter._
    let (string("_yyu_")) (in (a =>
    let (fetch(a))        (in (b =>
    let (getScreeName(b)) (in (c =>
    let (update(c))       (in (d =>
      delete(d)
    )))))))).run(DefaultEnvironment.defaultEnvironment)


  }
}
