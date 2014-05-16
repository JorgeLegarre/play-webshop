package controllers.privat;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public final class RestAutenticatedController extends
		Security.Authenticator {
	@Override
	public Result onUnauthorized(Context arg0) {
		return forbidden();
	}
}
