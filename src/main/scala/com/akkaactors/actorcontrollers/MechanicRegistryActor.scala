package com.akkaactors.actorcontrollers

import akka.actor.AbstractActor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import com.akkaactors.db.doa.MechanicDao
import com.akkaactors.db.models.Mechanic
import com.akkaactors.db.models.definition.MechanicId
import com.akkaactors.jsonsupport.JsonSupport

import scala.concurrent.Future
import scala.util.{Failure, Success}

object MechanicRegistryActor extends Commons {
  final case class GetMechanic(id: MechanicId)
  final case object GetMechanics
  final case class CreateMechanic(mechanic: Mechanic)
  final case class DeleteMechanic(id: MechanicId)

  def props: Props = Props[MechanicRegistryActor]
}

class MechanicRegistryActor extends JsonSupport with Actor with ActorLogging {
  import MechanicRegistryActor._
  import context.dispatcher

  def receive: Receive = {
    case CreateMechanic(mechanic) =>
      MechanicDao.create(mechanic)
      sender() ! ActionPerformed(s"Mechanic ${mechanic.username} Created")
    //allMechanics can be treated as a  monad

    case GetMechanic(id) =>
      val newSender = sender()
      val mechanic :Future[Mechanic] = MechanicDao.findById(id)

      mechanic.onComplete {
        case Success(mech) => newSender ! mech
        case Failure(data) => println(s"$id user not found")
      }
  }

}