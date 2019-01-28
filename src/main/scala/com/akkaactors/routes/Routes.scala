package com.akkaactors.routes

import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import scala.concurrent.duration._

trait Routes extends UserRoutes with MechanicRoutes {

  override lazy val timeout = Timeout(10.seconds) //This is not optimal

  val allRoutes = pathPrefix("app") {
    userRoutes ~
      mechanicRoutes
  }
}