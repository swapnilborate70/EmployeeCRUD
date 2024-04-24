package com.test.router;

import com.test.constant.Constant;
import com.test.constant.ResponseConstants;
import com.test.response.Response;
import com.test.service.Services;
import com.test.validation.Validation;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class APIRouter {
  public Services service;

  public Validation validate;

  public APIRouter(Router router, Services service, String path, Validation validation) {
    this.service= service;
    this.validate=validation;

    router.post(path).handler(this::validate).handler(this::create);
    router.get(path+"/:id").handler(this::get);
    router.get(path+"s").handler(this::getAll);
    router.delete(path+"/:id").handler(this::delete);
    router.put(path+"/:id").handler(this::validate).handler(this::update);
  }

  private void update(RoutingContext routingContext) {
    JsonObject document = routingContext.body().asJsonObject();
    int id = Integer.parseInt(routingContext.pathParam(Constant.PATH_VARIABLE));

    service.update(id,document)
      .onSuccess(success ->{
        if(success.body() != null)
        {
          Response.response(routingContext,ResponseConstants.SUCCESS_CODE,ResponseConstants.UPDATE_SUCCESS_STATUS);
        } else Response.response(routingContext,ResponseConstants.NOT_FOUND_CODE,ResponseConstants.NOT_FOUND_STATUS);
      })
      .onFailure(fail -> Response.response(routingContext,ResponseConstants.FAILURE_CODE,fail.getMessage()));
  }

  private void delete(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam(Constant.PATH_VARIABLE));
    service.delete(id)
      .onSuccess(success ->{
        if(success.body() != null)
        {
          Response.response(routingContext,ResponseConstants.SUCCESS_CODE,ResponseConstants.DELETE_SUCCESS_STATUS);
        } Response.response(routingContext,ResponseConstants.NOT_FOUND_CODE,ResponseConstants.NOT_FOUND_STATUS);
      })
      .onFailure(fail -> Response.response(routingContext,ResponseConstants.FAILURE_CODE,fail.getMessage()));
  }

  private void getAll(RoutingContext routingContext) {
    service.getAll()
      .onSuccess(success ->{
        Response.jsonArrayResponse(routingContext,ResponseConstants.SUCCESS_CODE,success.body());
      })
      .onFailure(fail -> Response.response(routingContext,ResponseConstants.FAILURE_CODE,fail.getMessage()));
  }

  private void get(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam(Constant.PATH_VARIABLE));
    service.get(id)
      .onSuccess(success -> {
        JsonObject document = success.body();
        if(document != null)
        {
          Response.jsonResponse(routingContext,ResponseConstants.SUCCESS_CODE,document);
        } else Response.response(routingContext,ResponseConstants.NOT_FOUND_CODE,ResponseConstants.NOT_FOUND_STATUS);
      })
      .onFailure(fail -> {
        Response.response(routingContext,ResponseConstants.FAILURE_CODE,fail.getMessage());
      });
  }

  public void create(RoutingContext routingContext)
  {
    JsonObject document =  routingContext.body().asJsonObject();
    service.create(document)
      .onSuccess(success-> {
        if(success !=null)
        {
          Response.response(routingContext, ResponseConstants.CREATE_SUCCESS_CODE,ResponseConstants.CREATE_SUCCESS_STATUS);
        }
      })
      .onFailure(fail-> Response.response(routingContext,500,fail.getMessage()));
  }

  public void validate(RoutingContext routingContext)
  {
    JsonObject document = routingContext.body().asJsonObject();
    JsonObject failedKeys  = validate.validate(document);

    if(failedKeys.isEmpty())
    {
      routingContext.next();
    }
    else
    {
      Response.jsonResponse(routingContext,ResponseConstants.FAILURE_CODE,JsonObject.of(Constant.STATUS,ResponseConstants.VALIDATION_FAILED,Constant.FAILED_KEY_AND_VALUES,failedKeys));
    }
  }
}
