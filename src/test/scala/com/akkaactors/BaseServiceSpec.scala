package com.akkaactors

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.akkaactors.db.doa.BaseDao
import com.akkaactors.db.models.User
import org.scalatest._
import com.akkaactors.db.util.MigrationConfig

import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with MigrationConfig
  with BaseDao {

  import driver.api._

  //protected val log: LoggingAdapter = NoLogging
//  val testUsers = Seq(
//    User(Some(1), "Tom", "Olowojos", "userTom", "usertom@gmail.com", "tomtom", "Lagos", "male"),
//    User(Some(2), "Jerry", "Palmer", "userJenny", "userJemmy@gmail.com", "jennyjenny", "Ogun", "Female"),
//    User(Some(3), "Frank", "Donga", "userFrank", "franky@gmail.com", "frankyfrank", "Jos", "Male"))

  reloadSchema()
//  Await.result(usersTable ++= testUsers, 10.seconds)
}