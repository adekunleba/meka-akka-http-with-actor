package com.akkaactors

import akka.http.scaladsl.model.{ HttpEntity, MediaTypes, StatusCodes }
import akka.actor.ActorRef
import akka.http.scaladsl.model._
import com.akkaactors.actorcontrollers.MechanicRegistryActor
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.server.Route
import com.akkaactors.routes.MechanicRoutes

class MechanicApiSpec extends BaseServiceSpec with MechanicRoutes with ScalaFutures {

  override val mechanicRegistryActor: ActorRef = system.actorOf(
    MechanicRegistryActor.props,
    "mechanicRegistryActor")
  lazy val routes: Route = mechanicRoutes
  "User routes" should {
    "Create Mechanic " in {
      val requestEntity = HttpEntity(
        MediaTypes.`application/json`,
        JsObject(
          "surname" -> JsString("John"),
          "firstname" -> JsString("Bull"),
          "email" -> JsString("johnbull@gmail.com"),
          "username" -> JsString("JohnBull"),
          "password" -> JsString("bullybull"),
          "location" -> JsString("Badagry"),
          "gender" -> JsString("male")).toString())

      Post("/mechanic/enroll-mechanic", requestEntity) ~> routes ~> check {
        status should ===(StatusCodes.Created)

        response.status should be(StatusCode.int2StatusCode(201))

      }
    }
  }
}

