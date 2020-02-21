package controllers

import javax.inject._
import play.api._
import play.api.i18n.I18nSupport
import play.api.mvc._
import service.HotelService
import play.api.libs.json._
import play.api.libs.json.Json._
import model.Hotel
import play.api.http.{DefaultHttpRequestHandler, HttpRequestHandler}
import play.api.routing.Router



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(override val controllerComponents: ControllerComponents, hotelService: HotelService) extends AbstractController(controllerComponents) with I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

//  def showMenu() = Action {
//    val set = hotelService.getDbMenu()
//    Ok(views.html.showMenu(stringify(toJson(set))))
//  }

  def showMenu1() = Action { implicit request: Request[AnyContent] =>
    if(request.session.get("userid") == None)
    {
      Ok(views.html.loginPage())
    }
    else {
      val set = hotelService.getDbMenu()
      request.session.get("userid").map(user => println(user))
      Ok(views.html.showMenu(set))
    }
  }

  def getForm() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.menuForm(hotelService.form))
  }

  def getFormOfHtml()= Action{implicit request: Request[AnyContent] =>
    Ok(views.html.menuForm1())
  }
  def add() = Action { implicit request: Request[AnyContent] =>
    println("inside add")
    val result =  hotelService.form.bindFromRequest.get
    val newMenu = Hotel(result.id,result.dishname,result.price)
    hotelService.addNewMenu(newMenu)
    Redirect("/getdata")
  }

  def delete(id:Int) = Action { implicit request: Request[AnyContent] =>
    println(id)
    if(hotelService.abc(id)==1)
      {
        hotelService.deleteCartDb(id)
      }
    hotelService.deleteMenuDb(id)
    Redirect("/getdata")
  }

  def update(id:Int) = Action { implicit request: Request[AnyContent] =>
    val set = hotelService.findMenu(id)
    Ok(views.html.updateForm(set))
  }

  def updated() = Action { implicit request: Request[AnyContent] =>
    println("inside update method")
    val result = hotelService.form.bindFromRequest.get
    hotelService.updateNewMenu(result)
    Redirect("/getdata")
  }

  def addtocart(id:Int) = Action { implicit request: Request[AnyContent] =>
    println("inside cart")
    println("asdasdasdasdas "+hotelService.abc(id))
    val count = hotelService.abc(id)
    if(count == 1)
      {
        Redirect("/getdata")
      }
    else
      {
        var cartAddition = hotelService.findMenu(id)
        val cartFinalValue = hotelService.maintainCart(cartAddition)
        println("inside controller value "+cartFinalValue)
        Redirect("/getdata")
      }
  }

  def showCart() = Action {
    val cartValue = hotelService.getDbCart()
    Ok(views.html.cartMenu(cartValue))
  }

  def removeFromcart(id:Int) = Action { implicit request: Request[AnyContent] =>
    hotelService.deleteCartDb(id)
    Redirect("/getcart")
  }

  def close() = Action { implicit request : Request[AnyContent]=>
    println("close  method")
    Redirect("/login").withNewSession
  }


}
