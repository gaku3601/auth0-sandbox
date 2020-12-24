package controllers

import javax.inject._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Json, Reads, Writes}
import play.api.mvc._

case class CreateRequest(nickname: String, age: Int)

object CreateRequest {
  implicit val CreateReads: Reads[CreateRequest] =
    (JsPath \ "nickname").read[String](minLength[String](4) keepAnd maxLength[String](30))
      .and((JsPath \ "age").read[Int](min[Int](0) keepAnd max[Int](150)))(CreateRequest.apply _)
}

case class CreateResponse()

object CreateResponse {
  implicit val CreateWrites: Writes[CreateResponse] = (res: CreateResponse) => Json.obj("message" -> "success")
}

@Singleton
class ProfileController @Inject()(val controllerComponents: ControllerComponents, val utils: ControllerUtils) extends BaseController {
  def create: Action[CreateRequest] = Action(utils.validateJson[CreateRequest]) { request =>
    val value = request.body
    println(value)
    Ok(Json.toJson(CreateResponse()))
  }
}
