package com.akkaactors.actorcontrollers

//#user-registry-actor

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import com.akkaactors.db.doa.UsersDao
import com.akkaactors.db.models.definition.UserId
import com.akkaactors.db.models.{ User, Users }
import com.akkaactors.jsonsupport.JsonSupport

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }
import spray.json._

//#user-case-classes

//#user-case-classes

object UserRegistryActor extends Commons {
  final case object GetUsers
  final case class CreateUser(user: User)
  final case class GetUser(name: UserId)
  final case class DeleteUser(name: UserId)

  def props: Props = Props[UserRegistryActor]
}

class UserRegistryActor extends JsonSupport with Actor with ActorLogging {
  import UserRegistryActor._
  import context.dispatcher
  //DB Implementation here

  def receive: Receive = {
    case GetUsers =>
      //When sending a future to an actor, it is important to extract the sender first before sending.
      val mysender = sender
      val allUsers = UsersDao.findAll
      allUsers.onComplete {
        case Success(usr) => mysender ! Users(usr)
        case Failure(failureUsr) => println("Data not found to find all Users in Database")
      }
    case CreateUser(user) =>
      UsersDao.create(user)
      sender() ! ActionPerformed(s"User ${user.username} created.")

    case GetUser(id) =>
      val user = UsersDao.findById(id)
      val userSender = sender
      userSender ! user //Send all future manipulations Error or not to route

    case DeleteUser(id) =>
      val user = UsersDao.delete(id)
      val delSender = sender
      user.onComplete {
        case Success(del) => delSender ! ActionPerformed(s"User $id deleted")
        case Failure(delUser) => println(s"Unable to Delete user $id")
      }
  }
}
//#user-registry-actor
//TODO: Implement Location Manipulation Using Google Map - Get the  distance between two locations
//Location supplied by the User in time, and location of nearby Mechanic.