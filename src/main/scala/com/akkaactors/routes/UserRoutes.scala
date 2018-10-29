package com.akkaactors.routes

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{ delete, get, post }
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout
import com.akkaactors.actorcontrollers.UserRegistryActor._
import com.akkaactors.jsonsupport.JsonSupport
import com.akkaactors.db.models.{ User, Users }

import scala.concurrent.Future
import scala.concurrent.duration._
import spray.json._

//#user-routes-class
trait UserRoutes extends JsonSupport {
  //#user-routes-class

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[UserRoutes])

  // other dependencies that UserRoutes use
  def userRegistryActor: ActorRef

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  //#all-routes
  //#users-get-post
  //#users-get-delete
  //Define Routes

  def userRoutes: Route = pathPrefix("users") {
    pathPrefix("enroll-user") {
      pathEnd {
        concat(
          get {
            val users: Future[Users] =
              (userRegistryActor ? GetUsers).mapTo[Users]
            complete(users)
          },
          post {
            entity(as[User]) { user =>
              val userCreated = (userRegistryActor ? CreateUser(user)).mapTo[ActionPerformed]
              onSuccess(userCreated) { performed =>
                log.info(s"Created User [${user.username}]: ${performed.description}")
                complete(StatusCodes.Created, performed.description)
              }
            }
          })
      } ~ //Because we allow the DB to add the numbers hence id's are just 1,2,3 etc.
        path(IntNumber) { id =>
          get {
            val maybeUser: Future[User] = (userRegistryActor ? GetUser(id)).mapTo[User]
            rejectEmptyResponse {
              complete(maybeUser)
            }
          } ~
            delete {
              val userDeleted: Future[ActionPerformed] = (userRegistryActor ? DeleteUser(id)).mapTo[ActionPerformed]
              onSuccess(userDeleted) { performed =>
                log.info(s"Deleted user $id", performed.description)
                complete(StatusCodes.OK, performed)
              }
            }
        }
    }
  }
}

//What does Path(Segment) means, it means we look for the last part of the url that does not have an ending slash
//Then the string is extracted.