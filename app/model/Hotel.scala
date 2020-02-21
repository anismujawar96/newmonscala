package model

import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


case class Hotel(id:Int,dishname:String,price:Int)

object Hotel {

  implicit val reads: Reads[Hotel] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "dishname").read[String] and
      (JsPath \ "price").read[Int]
    )(Hotel.apply _)

  implicit val writes: Writes[Hotel] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "dishname").write[String] and
      (JsPath \ "price").write[Int]
    )(unlift(Hotel.unapply))

}