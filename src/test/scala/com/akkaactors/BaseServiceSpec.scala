package com.akkaactors

import akka.event.{ LoggingAdapter, NoLogging }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.akkaactors.db.doa.BaseDao
import com.akkaactors.db.models.User
import com.akkaactors.db.util.MigrationConfig
import com.akkaactors.routes.Routes
import org.scalatest._
import com.akkaactors.db.util.MigrationConfig

import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes with MigrationConfig
  with BaseDao {

  import driver.api._

  //protected val log: LoggingAdapter = NoLogging
  val testUsers = Seq(
    User(Some(1), "userTom", "tomtom", "Lagos", 1),
    User(Some(2), "userJenny", "jennyjenny", "Ogun", 0),
    User(Some(3), "userFrank", "frankyfrank", "Jos", 1))

  reloadSchema()
  Await.result(usersTable ++= testUsers, 10.seconds)
}