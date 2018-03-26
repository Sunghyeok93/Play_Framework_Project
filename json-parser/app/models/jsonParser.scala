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

  // Json deserializer -> validate 하기위한 Reads[T] implicit 선언
  implicit val parameterReads: Reads[Parameter] = (
    (JsPath \ "min").read[Int] and
      (JsPath \ "max").read[Int] and
      (JsPath \ "values").read[Seq[String]] and
      (JsPath \ "range").read[String] and
      (JsPath \ "punctuation").read[String])(Parameter.apply _)

  implicit val parameterWrites : Writes[Parameter] = (
    (JsPath \ "min").write[Int] and
      (JsPath \ "max").write[Int] and
      (JsPath \ "values").write[Seq[String]] and
      (JsPath \ "range").write[String] and
      (JsPath \ "punctuation").write[String])(unlift(Parameter.unapply))

  implicit val optionReads : Reads[Option] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "values").read[Seq[String]] and
      (JsPath \ "mandatory").read[Boolean] and
      (JsPath \ "duplicated").read[Boolean] and
      (JsPath \ "parameter").read[Parameter] and
      (JsPath \ "flag").read[Boolean] and
      (JsPath \ "options").readNullable[Seq[Option]])(Option.apply _)

  implicit val optionWrites : Writes[Option] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "values").write[Seq[String]] and
      (JsPath \ "mandatory").write[Boolean] and
      (JsPath \ "duplicated").write[Boolean] and
      (JsPath \ "parameter").write[Parameter] and
      (JsPath \ "flag").write[Boolean] and
      (JsPath \ "options").writeNullable[Seq[Option]])(unlift(Option.unapply))

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


  def validate : Boolean = {
    val validateJson : JsResult[Tool]= json.validate[Tool]

    validateJson match
    {
      case s: JsSuccess[String] => true
      case e: JsError => false
    }

  }

  def printJson: String =
  {
    var result : String = ""
    val tool: Tool = (json).as[Tool]

    result = result + "toolName           : " + tool.name + '\n'
    result = result + "toolVersion        : " + tool.version + '\n'
    result = result + "toolJava           : " + tool.java + '\n'

    //Tool -> SubCommands 개수
    val toolSubCommandsNum : Int = tool.subCommands.length
    result = result + "toolSubCommandsNum : " + toolSubCommandsNum + '\n'

    result = result + "----------------------------------------------------------------------------" + '\n'
    //Tool -> SubCommands
    for(i <- 0 until toolSubCommandsNum) {
      result = result + depth(1) + "toolSubCommands[" + i + "]Name        : " + tool.subCommands(i).name + '\n'
      result = result + depth(1) + "toolSubCommands[" + i + "]Punctuation : " + tool.subCommands(i).punctuation + '\n'

      //Tool -> SubCommands -> Input 개수
      val SubCommandsInputsNum = tool.subCommands(i).inputs.length
      result = result + depth(1) + "toolSubCommands[" + i + "]InputsNum   : " + SubCommandsInputsNum + '\n'

      //Tool -> Subcommands -> Input
      for(j <- 0 until SubCommandsInputsNum){
        result = result + depth(2) + "toolSubCommands[" + i + "]Inputs[" + j +"]Name        : " + tool.subCommands(i).inputs(j).name + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Inputs[" + j +"]Mandatory   : " + tool.subCommands(i).inputs(j).mandatory + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Inputs[" + j +"]Punctuation : " + tool.subCommands(i).inputs(j).punctuation + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Inputs[" + j +"]Files       : " + tool.subCommands(i).inputs(j).files + '\n'
      }

      //Tool -> SubCommands -> Output 개수
      val SubCommandsOutputsNum = tool.subCommands(i).outputs.length
      result = result + depth(1) + "toolSubCommands[" + i + "]OutputsNum  : " + SubCommandsOutputsNum + '\n'

      //Tool -> SubCommands -> Output
      for(j <- 0 until SubCommandsOutputsNum){
        result = result + depth(2) + "toolSubCommands[" + i + "]Outputs[" + j +"]Name       : " + tool.subCommands(i).outputs(j).name + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Outputs[" + j +"]Mandatory  : " + tool.subCommands(i).outputs(j).mandatory + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Outputs[" + j +"]Punctuation: " + tool.subCommands(i).outputs(j).punctuation + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Outputs[" + j +"]Files      : " + tool.subCommands(i).outputs(j).files + '\n'
      }

      //Tool -> SubCommands -> Option 개수
      val SubCommandsOptionsNum = tool.subCommands(i).options.length
      result = result + depth(1) + "toolSubCommands[" + i + "]OptionsNum  : " + SubCommandsOptionsNum + '\n'

      //Tool -> SubCommands -> Option
      for(j <- 0 until SubCommandsOptionsNum){
        result = result + depth(2) + "toolSubCommands[" + i + "]Options[" + j +"]Name       : " + tool.subCommands(i).options(j).name + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Options[" + j +"]Values     : " + tool.subCommands(i).options(j).values + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Options[" + j +"]Mandatory  : " + tool.subCommands(i).options(j).mandatory + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Options[" + j +"]Duplicated : " + tool.subCommands(i).options(j).duplicated + '\n'
        result = result + depth(2) + "toolSubCommands[" + i + "]Options[" + j +"]Flag       : " + tool.subCommands(i).options(j).flag + '\n'

        ////Tool -> SubCommands -> Option -> Parameter
        result = result + depth(3) + "toolSubCommands[" + i + "]Options[" + j +"]ParameterMin    : " + tool.subCommands(i).options(j).parameter.min + '\n'
        result = result + depth(3) + "toolSubCommands[" + i + "]Options[" + j +"]ParameterMax    : " + tool.subCommands(i).options(j).parameter.max + '\n'
        result = result + depth(3) + "toolSubCommands[" + i + "]Options[" + j +"]ParameterValues    : " + tool.subCommands(i).options(j).parameter.values + '\n'
        result = result + depth(3) + "toolSubCommands[" + i + "]Options[" + j +"]ParameterRange    : " + tool.subCommands(i).options(j).parameter.range + '\n'
        result = result + depth(3) + "toolSubCommands[" + i + "]Options[" + j +"]ParameterPunctuation    : " + tool.subCommands(i).options(j).parameter.punctuation + '\n'

        //Tool -> SubCommands -> Option -> Option 개수(재귀적) - 미구현
        result = result + depth(2) + "toolSubCommands[" + i + "]Options[" + j +"]Option     : " + tool.subCommands(i).options(j).options + '\n'
      }

      result = result + "----------------------------------------------------------------------------" + '\n'
    }
    result
  }

  def sendJson() : Boolean =
  {
    true
  }

  val inputTest: JsResult[Input] = json.validate[Input]

  def depth(num : Int) : String =
  {
    var result = ""
    for(i  <- 0 until num)
      result = result + "   "
    result
  }
}
