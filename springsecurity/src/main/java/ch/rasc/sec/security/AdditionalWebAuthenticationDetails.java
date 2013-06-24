package ch.rasc.sec.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AdditionalWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String code;
	
	public AdditionalWebAuthenticationDetails(HttpServletRequest request) {
		super(request);		
	}

	public String getCode() {
		return code;
	}
	
	

}
