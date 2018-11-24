package com.akkaactors.db.models.definition

import com.akkaactors.db.models.User
import slick.jdbc.MySQLProfile.api._

class UsersTable(tag: Tag) extends Table[User](tag, "users") {

  def id = column[UserId]("id", O.PrimaryKey, O.AutoInc)
  def surname = column[String]("surname")
  def firstname = column[String]("firstname")
  def username = column[String]("username")
  def email = column[String]("email")
  def password = column[String]("password")
  def location = column[String]("location")
  def gender = column[String]("gender_c")

  //Add id to *
  def * = (id.?, firstname, surname, username, email, password, location, gender) <>
    ((User.apply _).tupled, User.unapply)
}