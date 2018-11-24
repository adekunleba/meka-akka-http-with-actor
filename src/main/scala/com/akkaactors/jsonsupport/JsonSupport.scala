package com.akkaactors.jsonsupport

import com.akkaactors.actorcontrollers.UserRegistryActor.ActionPerformed
import com.akkaactors.db.models.{ Mechanic, User, Users }

//#json-support
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat8(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val mechanicJsonFormat = jsonFormat8(Mechanic)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
  //implicit val entityFailure = jsonFormat1(EntityFailure)
}
//#json-support
