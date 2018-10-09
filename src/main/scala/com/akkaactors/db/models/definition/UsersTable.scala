package com.akkaactors.db.models.definition

import com.akkaactors.db.models.User
import slick.jdbc.PostgresProfile.api._

class UsersTable(tag: Tag) extends Table[User](tag, "users") {

  def id = column[UserId]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username")
  def password = column[String]("password")
  def location = column[String]("location")
  def gender = column[Int]("gender")

  //Add id to *
  def * = (id.?, username, password, location, gender) <> ((User.apply _).tupled, User.unapply)
}