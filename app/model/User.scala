package model

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

case class User(userName:String,password:String)

object User{

  implicit val reads: Reads[User] = (
      (JsPath \ "userName").read[String] and
      (JsPath \ "password").read[String]
    )(User.apply _)


  implicit val writes: Writes[User] = (
      (JsPath \ "userName").write[String] and
      (JsPath \ "password").write[String]
    )(unlift(User.unapply))

}
