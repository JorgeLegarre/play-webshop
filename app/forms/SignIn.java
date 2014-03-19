package forms;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;

public final class SignIn {
	@Required
	@Constraints.Email
	public String email;

	@Required
	public String password;
}
