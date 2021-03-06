package $package$.$name;format="camel"$.routes

import $package$.$name;format="camel"$.helpers.GuiceRandomDbName
import $package$.$name;format="camel"$.model.generated.NameResponse
import org.scalatest.{Assertion, BeforeAndAfterAll, FlatSpec, Matchers}
import play.api.libs.json.Json
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._

class NameRouterSpec
    extends FlatSpec with Results with Matchers with GuiceRandomDbName with BeforeAndAfterAll {

  import $package$.$name;format="camel"$.conversions.ModelConversions._

  behavior of "GET /count"
  it should "return 'Hello, Anonymous' when no query parameters is provided" in {
    val Some(result) = route(app, FakeRequest("GET", "/count"))
    contentAsString(result) shouldBe "Hello, Anonymous"
  }

  behavior of "GET /count?name="
  it should "return 'Hello, Anonymous'" in {
    Thread.sleep(1000)
    val Some(result) = route(app, FakeRequest("GET", "/count?name="))
    contentAsString(result) shouldBe "Hello, Anonymous"
  }

  behavior of "GET /count?name=TurangaLeela (3 times)"
  it should "increase the counter for 'TurangaLeela' up to 3" in {
    callAndTest(1)
    callAndTest(2)
    callAndTest(3)
  }

  it should "return a 429 - TooManyRequests" in {
    val Some(result) = route(app, FakeRequest("GET", "/count?name=TurangaLeela"))
    status(result) shouldBe TOO_MANY_REQUESTS
  }

  private def callAndTest(times: Int): Assertion = {
    Thread.sleep(1000)
    val Some(result3) = route(app, FakeRequest("GET", "/count?name=TurangaLeela"))
    contentAsJson(result3) shouldBe Json.toJson(NameResponse("TurangaLeela", times))
  }
}
