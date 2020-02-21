package service

import com.mongodb.BasicDBObject
import model.{CartMenu, Hotel}
import org.mongodb.scala.bson.{BsonDocument, BsonInt32, BsonNumber, BsonString}
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.model.Filters.equal

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.mongodb.scala.{Completed, Document, FindObservable, Observable, Observer, SingleObservable}
import play.api.data.Forms._
import play.api.data.Form


class HotelService {

  def getDbMenu() = {
    val docs: FindObservable[Document] = Db.db.getCollection("Menu").find()
    val resp = Await.result(docs.toFuture(), Duration.Inf)
    val a = resp.map {
      doc =>
        val userId = doc("id").asInt32().getValue
        val menuName = doc("dishname").asString().getValue
        val price = doc("price").asInt32().getValue
        Hotel(userId, menuName, price)
    }.toList
    println("resp=====>" + a)
    a
  }

  val form: Form[Hotel] = Form(
    mapping(
      "id" -> number,
      "dishname" -> text,
      "price" -> number
    )(Hotel.apply _)(Hotel.unapply)
  )

  def addNewMenu(newHotel: Hotel) = {
    // val newset: Document = Document("id"->newHotel.id,"dishname"->newHotel.dishname,"price"->newHotel.price)
    //val newset: BsonDocument = BsonDocument("id"->newHotel.id,"dishname"->newHotel.dishname,"price"->newHotel.price)
    // val res = Document(Hotel({newHotel.id,newHotel.dishname,newHotel.price}))
    val newset: BsonDocument = BsonDocument("id" -> BsonInt32(newHotel.id), "dishname" -> BsonString(newHotel.dishname), "price" -> BsonInt32(newHotel.price))
    val observe: SingleObservable[Completed] = Db.db.getCollection("Menu").insertOne(newset)
    observe.subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println("Inserted")
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = println("Completed")
    })
  }

  def deleteMenuDb(id: Int) = {
    val idmenu = BsonDocument("id" -> BsonInt32(id))
    val oberver: SingleObservable[DeleteResult] = Db.db.getCollection("Menu").deleteOne(idmenu)
    oberver.subscribe(new Observer[DeleteResult] {
      override def onNext(result: DeleteResult): Unit = println("Deleted")
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = println("Completed")
    })
  }

  def findMenu(id: Int) = {
    val idmenu = BsonDocument("id" -> BsonInt32(id))
    val result1 = Db.db.getCollection("Menu").find(idmenu)
    val resp = Await.result(result1.toFuture(), Duration.Inf)
    val a = resp.map {
      doc =>
        val userId = doc("id").asInt32().getValue
        val menuName = doc("dishname").asString().getValue
        val price = doc("price").asInt32().getValue
        Hotel(userId, menuName, price)
    }.toList
    println("resp=====>" + a)
    a
  }

  def updateNewMenu(newHotel: Hotel) = {
    val temp = BsonDocument("id" -> BsonInt32(newHotel.id), "dishname" -> BsonString(newHotel.dishname), "price" -> BsonInt32(newHotel.price))
    val basicDBObject = new BasicDBObject().append("$set", temp)
    val observe: SingleObservable[UpdateResult] = Db.db.getCollection("Menu").updateOne(equal("id", newHotel.id), basicDBObject)
    observe.subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = println("updated")
      override def onError(e: Throwable): Unit = println("failed")
      override def onComplete(): Unit = println("completed")
    })
  }

  def maintainCart(x:List[Hotel]) = {
    val result1 = x.take(1)
    result1.foreach(c=>{
      val newHotel = Hotel(c.id,c.dishname,c.price)
      val newset: BsonDocument = BsonDocument("id" -> BsonInt32(newHotel.id), "dishname" -> BsonString(newHotel.dishname), "price" -> BsonInt32(newHotel.price))
      val observe: SingleObservable[Completed] = Db.db.getCollection("cartData").insertOne(newset)
      observe.subscribe(new Observer[Completed] {
        override def onNext(result: Completed): Unit = println("Inserted")
        override def onError(e: Throwable): Unit = println("Failed")
        override def onComplete(): Unit = println("Completed")
      })
    })
  }

  def getDbCart() = {
    val docs: FindObservable[Document] = Db.db.getCollection("cartData").find()
    val resp = Await.result(docs.toFuture(), Duration.Inf)
    val a = resp.map {
      doc =>
        val userId = doc("id").asInt32().getValue
        val menuName = doc("dishname").asString().getValue
        val price = doc("price").asInt32().getValue
        Hotel(userId,menuName,price)
    }.toList
    println("resp=====>" + a)
    a
  }

  def deleteCartDb(id: Int) = {
    val idmenu = BsonDocument("id" -> BsonInt32(id))
    val oberver: SingleObservable[DeleteResult] = Db.db.getCollection("cartData").deleteOne(idmenu)
    oberver.subscribe(new Observer[DeleteResult] {
      override def onNext(result: DeleteResult): Unit = println("Deleted")
      override def onError(e: Throwable): Unit = println("Failed")
      override def onComplete(): Unit = println("Completed")
    })
  }

  def abc(id:Int)={
    val abc = findCart(id)
    val ert: Int = abc.size
    ert
  }

  def findCart(id: Int) = {
    val idmenu = BsonDocument("id" -> BsonInt32(id))
    val result1 = Db.db.getCollection("cartData").find(idmenu)
    val resp = Await.result(result1.toFuture(), Duration.Inf)
    val a = resp.map {
      doc =>
        val userId = doc("id").asInt32().getValue
        val menuName = doc("dishname").asString().getValue
        val price = doc("price").asInt32().getValue
        Hotel(userId, menuName, price)
    }.toList
    println("resp=====>" + a)
    a
  }

}
