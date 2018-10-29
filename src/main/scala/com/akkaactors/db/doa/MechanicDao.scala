package com.akkaactors.db.doa

import com.akkaactors.db.models.Mechanic
import com.akkaactors.db.models.definition.MechanicId
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

object MechanicDao extends BaseDao {

  def create(mechanic: Mechanic): Future[MechanicId] = mechanicTable.returning(mechanicTable.map(_.id)) += mechanic
  def findById(mechanicId: MechanicId): Future[Mechanic] = mechanicTable.filter(_.id === mechanicId).result.head

  def findAll: Future[Seq[Mechanic]] = mechanicTable.result


}