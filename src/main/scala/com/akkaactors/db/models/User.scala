package com.akkaactors.db.models

import com.akkaactors.db.models.definition.{ MechanicId, UserId }

case class User(id: Option[UserId], username: String, password: String, location: String, gender: Int)

case class Users(users: Seq[User])

case class Mechanic(id: Option[MechanicId], firstname: String, surname: String, username: String,
  password: String, location: String, gender: Int)

case class Mechanics(mechanics: Mechanics)