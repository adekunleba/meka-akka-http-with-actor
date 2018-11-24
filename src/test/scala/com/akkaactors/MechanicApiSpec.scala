package com.akkaactors

import akka.http.scaladsl.model.{ HttpEntity, MediaTypes, StatusCodes }
import akka.actor.ActorRef
import akka.http.scaladsl.model._
import com.akkaactors.actorcontrollers.MechanicRegistryActor
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.server.Route
import com.akkaactors.BaseServiceSpec
import com.akkaactors.routes.MechanicRoutes

class MechanicApiSpec extends BaseServiceSpec with MechanicRoutes with ScalaFutures {

  override val mechanicRegistryActor: ActorRef = system.actorOf(
    MechanicRegistryActor.props,
    "mechanicRegistryActor")
  lazy val routes: Route = mechanicRoutes

  "Mechanic "
}

