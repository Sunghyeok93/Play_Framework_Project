package controllers

import javax.inject._

import models.jsonParser
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess}
import play.api.mvc._
import play.libs.Scala
import play.libs.ws.WS

import scala.io.Source

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {

    val s1 = Source.fromFile("/Users/sunghyeok/Jsonfile/test.json").mkString
    //val s2 = Source.fromFile("/Users/sunghyeok/Jsonfile/test2.json").mkString
    val parser1 = new jsonParser(s1)
    //val parser2 = new jsonParser(s2)

    //Logger.error(parser.tool + parser.version + parser.java)
    Logger.error("here")

    if(parser1.validate) {
      parser1.sendJson
      Ok(parser1.printJson)
    }
    else {
      Ok("json parsing failed")
    }
    /*parser2.inputTest match
    {
      case s: JsSuccess[String] => Ok("Success : " +  s.get)
      case e: JsError => Ok("Error : " + JsError.toJson(e).toString())
    }
*/
    //val testCode = parser.lat
    //Ok(parser1.toString)
  }

}
