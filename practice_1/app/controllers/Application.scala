package controllers

import javax.inject.Inject

import play.api.mvc.{AbstractController, Action, Controller, ControllerComponents}
import services.Counter

class Application extends Controller{

  def index = Action {
    Redirect(routes.Products.list())
  }
}
