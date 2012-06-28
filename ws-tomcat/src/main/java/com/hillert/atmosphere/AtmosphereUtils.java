package com.hillert.atmosphere;

import javax.servlet.http.HttpServletRequest;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Meteor;

public final class AtmosphereUtils {

	public static AtmosphereResource getAtmosphereResource(final HttpServletRequest request) {
		return getMeteor(request).getAtmosphereResource();
	}

	public static Meteor getMeteor(final HttpServletRequest request) {
		return Meteor.build(request);
	}

}
