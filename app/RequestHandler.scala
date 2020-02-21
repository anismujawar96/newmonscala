
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
//import com.google.inject.Inject
import play.api.http.HttpRequestHandler
import play.api.mvc.{DefaultActionBuilder, RequestHeader, Results}
import play.api.routing.Router
import akka.http.scaladsl._
import javax.inject.Inject
import javax.inject.Provider
import play.api.OptionalDevContext
import play.api.http._
import play.api.mvc.RequestHeader
import play.core.WebCommands
import play.api.mvc.Handler

//object bar {
//  type Routes = Router
//}
//object foo {
//  type Routes = Router
//}
//
//class VirtualHostRequestHandler @Inject() (
//webCommands: WebCommands,
//optionalDevContext: OptionalDevContext,
//errorHandler: HttpErrorHandler,
//configuration: HttpConfiguration,
//filters: HttpFilters,
//fooRouter: Provider[foo.Routes],
//barRouter: Provider[bar.Routes]) extends DefaultHttpRequestHandler(webCommands, optionalDevContext, fooRouter, errorHandler, configuration, filters) {
//  override def routeRequest(request: RequestHeader): Option[Handler] = {
//    request.host match {
//      case "foo.example.com" => fooRouter.get.routes.lift(request)
//      case "bar.example.com" => barRouter.get.routes.lift(request)
//      case _                 => super.routeRequest(request)
//    }
//  }
//}
//#virtualhost
//
class RequestHandler @Inject() (router: Router, action: DefaultActionBuilder) extends HttpRequestHandler {
  def handlerForRequest(request: RequestHeader) = {

    router.routes.lift(request) match {
      case Some(handler) => {
        val s: String = request.path
        println("inside someee"+request)
        (request,handler)}
      case None  => (request, action(Results.NotFound))
    }
  }
}