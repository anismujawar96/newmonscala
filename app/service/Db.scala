package service

import org.mongodb.scala.{MongoClient, MongoDatabase}

object Db {
  val uri: String = "mongodb://localhost:27017"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("Hotel")
  val collect = db.getCollection("Menu")
}
