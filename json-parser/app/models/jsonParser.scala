package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Parameter(min:Int, max:Int, values:Seq[String], range:String, punctuation:String)
case class Option(name:String, values:Seq[String], mandatory:Boolean, duplicated:Boolean, parameter:Parameter, flag:Boolean, options:scala.Option[Seq[Option]])
case class Input(name:String, mandatory:Boolean, files:Seq[String], punctuation:String)
case class Output(name:String, mandatory:Boolean, files:Seq[String], punctuation:String)
case class SubCommand(name: String, inputs: Seq[Input], outputs:Seq[Output], options: Seq[Option], punctuation: String)
case class Tool(name:String, version:String, java: Boolean, subCommands: Seq[SubCommand])

class jsonParser(jsonString: String) {
  val json: JsValue = Json.parse(jsonString)

  implicit val parameterReads: Reads[Parameter] = (
    (JsPath \ "min").read[Int] and
      (JsPath \ "max").read[Int] and
      (JsPath \ "values").read[Seq[String]] and
      (JsPath \ "range").read[String] and
      (JsPath \ "punctuation").read[String])(Parameter.apply _)

  implicit val optionReads: Reads[Option] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "values").read[Seq[String]] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "duplicated").read[Boolean] and
      (JsPath \ "parameter").read[Parameter] and
      (JsPath \ "flag").read[Boolean] and
      (JsPath \ "options").readNullable[Seq[Option]])(Option.apply _)

  implicit val inputReads: Reads[Input] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "files").read[Seq[String]] and
      (JsPath \ "punctuation").read[String])(Input.apply _)

  implicit val outputReads: Reads[Output] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "files").read[Seq[String]] and
      (JsPath \ "punctuation").read[String])(Output.apply _)

  implicit val subCommandReads: Reads[SubCommand] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "inputs").read[Seq[Input]] and
      (JsPath \ "outputs").read[Seq[Output]] and
      (JsPath \ "options").read[Seq[Option]] and
      (JsPath \ "punctuation").read[String])(SubCommand.apply _)

  implicit val toolReads: Reads[Tool] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "version").read[String] and
      (JsPath \ "java").read[Boolean] and
      (JsPath \ "subCommands").read[Seq[SubCommand]])(Tool.apply _)


  //val lat = (json \ "tool").get
  //val toolResult: JsResult[Tool] = json.validate[Tool]
  //val subCommandResult= (json \\ "subCommands").map(_.as[SubCommand])
  //val tmp3 = (subCommandResult(0) \ "name")
  //val tmp = subCommandResult.map(f => f.name.mkString)
  //val tmp2 = (json \ "subCommands").get

  /*
  //Tool Validation
  json.validate[Tool] match {
    case s: JsSuccess[Tool] => {
      val tool: Tool = s.get
      tool
    }
  }*/

  //val tool = (json \ "name").as[String]
  //val version = (json \ "version").as[String]
  //val java = (json \ "java").as[Boolean]
  //val subCommands = (json \ "subCommands").map(_.as[SubCommand])
  //val num = subCommands.length

  def validate=
  {
    val test : JsResult[Tool]= json.validate[Tool]

    test match {
      case s: JsSuccess[String] => System.out.println("Success : " +  s.get)
      case e: JsError => System.out.println("Error : " + JsError.toJson(e).toString())
    }

    System.out.println("name : " + (json \ "name").as[String])
    System.out.println("java : " + (json \ "java").as[Boolean])
    System.out.println("version : " + (json \ "version").as[String])
    System.out.println("SubCommands : " + (json \ "subCommands").as[Seq[SubCommand]].length)
  }

  val inputTest: JsResult[Input] = json.validate[Input]
}
