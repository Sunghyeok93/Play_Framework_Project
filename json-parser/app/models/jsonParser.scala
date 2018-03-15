package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Tool(name:String, version:String, java: Boolean, subCommand: Seq[SubCommand])
case class SubCommand(name: String, input: Seq[Input], output:Seq[Output], options: Seq[Option], punctuation: String)
case class Input(name:String, mandatory:Boolean, file:Seq[String], punctuation:String)
case class Output(name:String, mandatory:Boolean, file:Seq[String], punctuation:String)
case class Option(name:String, value:Seq[String], mandatory:Boolean, duplicated:Boolean, parameter:Parameter, flag:Boolean, option:Seq[Option])
case class Parameter(min:Int, max:Int, value:Seq[String], range:String, punctuation:String)

object jsonParser {

  implicit val toolReads: Reads[Tool] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "version").read[String] and
      (JsPath \ "java").read[Boolean] and
      (JsPath \ "subCommand").read[Seq[SubCommand]]
  )(Tool.apply _)

  implicit val subCommandReads: Reads[SubCommand] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "input").read[Seq[Input]] and
      (JsPath \ "output").read[Seq[Output]] and
      (JsPath \ "options").read[Seq[Option]] and
      (JsPath \ "punctuation").read[String]
  )(SubCommand.apply _)

  implicit val inputReads: Reads[Input] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "file").read[Seq[String]] and
      (JsPath \ "punctuation").read[String]
  )(Input.apply _)

  implicit val outputReads: Reads[Output] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "file").read[Seq[String]] and
      (JsPath \ "punctuation").read[String]
  )(Output.apply _)

  implicit val optionReads: Reads[Option] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "value").read[Seq[String]] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "duplicated").read[Boolean] and
      (JsPath \ "parameter").read[Parameter] and
      (JsPath \ "flag").read[Boolean] and
      (JsPath \ "option").read[Seq[Option]]
  )(Option.apply _)

  implicit val parameterReads: Reads[Parameter] = (
    (JsPath \ "min").read[Int] and
      (JsPath \ "max").
  )
}
case class Parameter(min:Int, max:Int, value:Seq[String], range:String, punctuation:String)