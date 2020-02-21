package service

import model.{Hotel, User}
import org.mongodb.scala.{Document, FindObservable, Observer}
import org.mongodb.scala.bson.{BsonDocument, BsonString}
import play.api.data.Forms.mapping
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Userservice {

  val form: Form[User] = Form(
    mapping(
      "userName" -> text,
      "password" -> text
    )(User.apply _)(User.unapply)
  )

  def findDbUser(user:User) = {
    val username = BsonDocument("userName" -> BsonString(user.userName))
    val result: FindObservable[Document] = Db.db.getCollection("Userdata").find(username)
    val resp = Await.result(result.toFuture(), Duration.Inf)
    val a = resp.map {
      doc =>
        val userName = doc("userName").asString().getValue
        val password = doc("password").asString().getValue
        User(userName,password)
    }.toList
    println("resp=====>" + a)
    a
  }

}
