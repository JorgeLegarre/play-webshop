package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import play.data.validation.ValidationError;
import play.mvc.Controller;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class GeneralController extends Controller {

	protected static Double getParamDouble(String parameterName) {
		List<Double> params = getListDouble(parameterName);

		if (params.size() > 0) {
			return params.get(0);
		}

		return 0.0;
	}

	protected static String getParamString(String parameterName) {
		List<String> params = getListString(parameterName);

		if (params.size() > 0) {
			return params.get(0);
		}

		return "";
	}

	protected static int getParamInt(String parameterName) {
		List<Integer> params = getListInt(parameterName);

		if (params.size() > 0) {
			return params.get(0);
		}

		return 0;
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
				try {
					retorno.add(Double.parseDouble(param));
				} catch (Exception e) {

				}
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
				try {
					retorno.add(Integer.parseInt(param));
				} catch (Exception e) {

				}
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

	protected static void prepareMsgErrors(
			Map<String, List<ValidationError>> errors, JsonNode errorsAsJson) {
		for (final String propertyName : errors.keySet()) {

			String errorMessage = errorsAsJson.findValue(propertyName).get(0)
					.asText().replace('[', ' ').replace(']', ' ');

			flash().put(propertyName + "-error", errorMessage);
		}
	}

}
