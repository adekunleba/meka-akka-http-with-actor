package com.akkaactors

import akka.http.scaladsl.model.{ HttpEntity, MediaTypes, StatusCodes }
import akka.actor.ActorRef
import akka.http.scaladsl.model._
import com.akkaactors.actorcontrollers.UserRegistryActor
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.server.Route
import com.akkaactors.routes.UserRoutes
import com.typesafe.scalalogging.LazyLogging

class UsersApiSpec extends BaseServiceSpec with UserRoutes with ScalaFutures with LazyLogging {

  //You should define registryActor as val and not a method.

  override val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistry")
  lazy val routes: Route = userRoutes

  "User routes" should {
    "retrieve user by id" in {
      Get("/users/enroll-user/1") ~> routes ~> check {
        status should be(StatusCodes.OK)
        //responseAs[JsObject] should be(testUsers.head.toJson)
      }
    }
    //Test1
    "Create User" in {
      val newUsername = "SomeUsername"
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
      //          "gender" -> JsNumber(1)).toString())

      Post("/users/enroll-user", requestEntity) ~> routes ~> check {
        status should ===(StatusCodes.Created)

        response.status should be(StatusCode.int2StatusCode(201))
      }
    }
  }
}
