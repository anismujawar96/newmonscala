package model

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._


case class CartMenu(id:Int,dishname:String,price:Int,quantity:Int)

object CartMenu {
  implicit val reads: Reads[CartMenu] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "dishname").read[String] and
      (JsPath \ "price").read[Int] and
      (JsPath \ "quantity").read[Int]
    )(CartMenu.apply _)

  implicit val writes: Writes[CartMenu] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "dishname").write[String] and
      (JsPath \ "price").write[Int] and
      (JsPath \ "quantity").write[Int]
    )(unlift(CartMenu.unapply))
}

