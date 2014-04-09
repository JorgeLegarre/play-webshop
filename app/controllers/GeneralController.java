package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import play.mvc.Controller;

public abstract class GeneralController extends Controller {

	protected static int getParamInt(String parameterName) {
		List<Integer> params = getListInt(parameterName);

		if (params.size() > 0) {
			return params.get(0);
		}

		return -1;
	}

	protected static boolean getParamBoolean(String parameterName) {
		List<String> params = getListString(parameterName);

		if (params.size() > 0) {

			return params.get(0) != null ? "on".equals(params.get(0)) : false;
		}

		return false;
	}

	protected static List<Double> getListDouble(String parameterName) {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] nameParameters = parameters.get(parameterName);

		List<Double> retorno = new ArrayList<>();
		if (nameParameters != null) {
			for (String param : nameParameters) {
				retorno.add(Double.parseDouble(param));
			}
		}
		return retorno;
	}

	protected static List<Integer> getListInt(String parameterName) {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] nameParameters = parameters.get(parameterName);

		List<Integer> retorno = new ArrayList<>();
		if (nameParameters != null) {
			for (String param : nameParameters) {
				retorno.add(Integer.parseInt(param));
			}
		}
		return retorno;
	}

	protected static List<String> getListString(String parameterName) {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] nameParameters = parameters.get(parameterName);

		return nameParameters != null ? Arrays.asList(nameParameters)
				: new ArrayList<String>();
	}

	protected static void enableEditionMode() {
		flash().put("edit", "yes");
	}

}
