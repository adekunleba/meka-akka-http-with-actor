package com.akkaactors.routes

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import com.akkaactors.jsonsupport.JsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.util.Timeout
import com.akkaactors.actorcontrollers.MechanicRegistryActor._
import com.akkaactors.db.models.Mechanic

import scala.concurrent.duration._
import akka.pattern.ask

import scala.concurrent.Future

trait MechanicRoutes extends JsonSupport {

  implicit def system: ActorSystem

  lazy val logging = Logging(system, classOf[MechanicRoutes])

  def mechanicRegistryActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

/***
    * Define the mechanic route including create, get all mechanic, get one mechanic by ID,
    * Delete mechanic.
    * @return
    */
  def mechanicRoutes: Route = pathPrefix("mechanic") {
    pathPrefix("enroll-mechanic") {
      pathEnd {
        post {
          entity(as[Mechanic]) { mechanic =>
            val created =
              (mechanicRegistryActor ? CreateMechanic(mechanic)).mapTo[ActionPerformed]
            onSuccess(created) { performed =>
              logging.info(s"Created User [${mechanic.username}]: ${performed.description}")
              complete(StatusCodes.Created, performed.description)
            }
          }
        }
      }
    } ~
      path(IntNumber) { id =>
        get {
          val maybeMechanic: Future[Mechanic] =
            (mechanicRegistryActor ? GetMechanic(id)).mapTo[Mechanic]
          rejectEmptyResponse {
            complete(maybeMechanic)
          }
        }
      }
  }
}