package com.akkaactors.db.models.definition
import com.akkaactors.db.models.Mechanic
import slick.jdbc.PostgresProfile.api._

class MechanicsTable(tag: Tag) extends Table[Mechanic](tag, "mechanicTable") {
  def id = column[UserId]("id", O.PrimaryKey, O.AutoInc)
  def firstname = column[String]("firstname")
  def surname = column[String]("surname")
  def username = column[String]("username")
  def password = column[String]("password")
  def location = column[String]("location")
  def gender = column[Int]("gender")

  //Add id to *
  def * = (id.?, username, firstname, surname, password, location, gender) <>
    ((Mechanic.apply _).tupled, Mechanic.unapply)
}