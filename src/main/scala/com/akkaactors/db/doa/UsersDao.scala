package com.akkaactors.db.doa

import com.akkaactors.db.models.User
import com.akkaactors.db.models.definition.UserId
import slick.jdbc.MySQLProfile.api._
import com.github.t3hnar.bcrypt._

import scala.concurrent.Future

object UsersDao extends BaseDao {

  def findAll: Future[Seq[User]] = usersTable.result
  def create(user: User): Future[UserId] = usersTable.returning(usersTable.map(_.id)) += user.copy(password = user.password.bcrypt)
  def findById(userId: UserId): Future[User] = usersTable.filter(_.id === userId).result.head

  def delete(userId: UserId): Future[Int] = usersTable.filter(_.id === userId).delete
}