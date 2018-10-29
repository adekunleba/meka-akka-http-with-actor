import akka.http.scaladsl.model.{HttpEntity, MediaTypes, StatusCodes}
import akka.actor.ActorRef
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.akkaactors.actorcontrollers.UserRegistryActor
import com.akkaactors.routes.UserRoutes
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class UsersApiSpec extends WordSpec with Matchers with ScalatestRouteTest
 with ScalaFutures with UserRoutes {

  override def userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistry")

  lazy val routes = userRoutes

  //Test1

  "UserRoutes" should {
    "return no users if no present (GET / users)" in {
      val request = HttpRequest(uri = "/users")


      request ~> routes ~> check {
        status should === (StatusCodes.OK)

        contentType should === (ContentTypes.`application/json`)

        //entityAs[String] should ===
      }
    }
  }
}
