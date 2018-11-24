package com.akkaactors

//#quick-start-server
import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration.Duration
import scala.util.{ Failure, Success }
import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.akkaactors.actorcontrollers.{ MechanicRegistryActor, UserRegistryActor }
import com.akkaactors.db.util.{ Config, MigrationConfig }
import com.akkaactors.routes.Routes
import com.typesafe.scalalogging.LazyLogging

//#main-class
object QuickstartServer extends App with Config with MigrationConfig with Routes with LazyLogging {

  logger.info("Starting application, creating execution context")
  implicit val system: ActorSystem = ActorSystem("mekabackend")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  //#server-bootstrapping

  logger.info("Actors are initializing")
  val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistryActor")
  val mechanicRegistryActor: ActorRef = system.actorOf(MechanicRegistryActor.props, "mechanicRegistryActor")
  //#main-class
  // from the UserRoutes trait
  lazy val routes: Route = allRoutes
  //#main-class

  logger.info("Flyway migrate for latest sql schema")
  migrate()

  //#http-server
  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 8080)

  serverBinding.onComplete {
    case Success(bound) =>
      logger.info(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      logger.error(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }

  //reloadSchema()
  Await.result(system.whenTerminated, Duration.Inf)

}

