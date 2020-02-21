package controllers

import javax.inject._
import model.Hotel
import play.api.http.HttpRequestHandler
import play.api.i18n.I18nSupport
import play.api.mvc._
import play.api.routing.Router
import service.{HotelService, Userservice}


@Singleton
class Usercontroller @Inject()(override val controllerComponents: ControllerComponents, userservice: Userservice) extends AbstractController(controllerComponents) with I18nSupport {


  def login() = Action { implicit request : Request[AnyContent] =>
    val s = request.session.get("userid")
    println("======> "+s)

    Ok(views.html.loginPage())
  }

  def validate() = Action { implicit request : Request[AnyContent] =>
    println("inside validate")
    val result = userservice.form.bindFromRequest.get
    val resultlist = userservice.findDbUser(result)
    if(resultlist.size==1)
      {if(result.password=="ani") { Redirect("/getdata").withSession("userid"->result.userName) }
        else { Redirect("/login") }
      }
    else { Redirect("/login") }
  }
}
