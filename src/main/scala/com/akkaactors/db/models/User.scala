package com.akkaactors.db.models

import com.akkaactors.db.models.definition.{ MechanicId, UserId }

//sealed trait ActingEntities
case class User(
  id: Option[UserId],
  firstname: String,
  surname: String,
  username: String,
  email: String,
  password: String,
  location: String,
  gender: String)
case class Users(users: Seq[User])

case class Mechanic(
  id: Option[MechanicId],
  firstname: String,
  surname: String,
  username: String,
  email: String,
  password: String,
  location: String,
  gender: String)

case class Mechanics(mechanics: Seq[Mechanics])

//case class EntityFailure(message: String) extends ActingEntities