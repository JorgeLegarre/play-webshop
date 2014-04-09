package controllers.publica;

import controllers.routes;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class PublicAutenticatedController extends Security.Authenticator {

	@Override
	public Result onUnauthorized(Context arg0) {
		return redirect(routes.SignInController.showPublicLogin());
	}

}
