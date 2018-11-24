import akka.http.scaladsl.model.{ HttpEntity, MediaTypes, StatusCodes }
import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.testkit.RouteTestTimeout
import com.akkaactors.actorcontrollers.{ MechanicRegistryActor, UserRegistryActor }
import com.akkaactors.db.models.User
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.akkaactors.BaseServiceSpec

import scala.concurrent.duration._

class UsersApiSpec extends BaseServiceSpec with ScalaFutures {

  //You should define registryActor as val and not a method.

  override val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistry")
  override val mechanicRegistryActor: ActorRef = system.actorOf(MechanicRegistryActor.props, "mechanicRegistry")
  lazy val routes = userRoutes

  "User routes" should {
    "retrieve user by id" in {
      Get("/users/enroll-user/1") ~> routes ~> check {
        status should be(StatusCodes.OK)
        //responseAs[JsObject] should be(testUsers.head.toJson)
      }
    }
    //Test1
    "Create User" in {
      val newUsername = "SomeUsername"
      val requestEntity = HttpEntity(
        MediaTypes.`application/json`,
        JsObject(
          "username" -> JsString("John Bull"),
          "password" -> JsString("bullybull"),
          "location" -> JsString("Badagry"),
          "gender" -> JsNumber(1)).toString())

      Post("/users/enroll-user", requestEntity) ~> routes ~> check {
        //status should ===(StatusCodes.Created)
        response.status should be(StatusCode.int2StatusCode(201))
      }
    }
  }
}
