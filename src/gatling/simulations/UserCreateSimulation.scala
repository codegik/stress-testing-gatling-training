import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import java.util.UUID
import scala.util.Random

class UserCreateSimulation extends Simulation {

    val httpProtocol = http
        .baseUrl("http://localhost:8182")
        .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")

    var randomString = Iterator.continually(Map("randstring" -> ( Random.alphanumeric.take(35).mkString )))

    val scn = scenario("Create users")
        .feed(randomString)
        .exec(http("Create user")
            .post("/user")
            .body(StringBody("""{ "firstName": "${randstring}", "lastName": "${randstring}", "email": "${randstring}", "gender": "${randstring}" }""")).asJson)

    setUp(scn.inject(
        atOnceUsers(100),
        rampUsers(60) during(20 seconds),
        constantUsersPerSec(60) during(60 seconds)
    )).protocols(httpProtocol)

}