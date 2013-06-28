package ch.rasc.sec.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AdditionalWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private Integer code;

	public AdditionalWebAuthenticationDetails(HttpServletRequest request) {
		super(request);

		String codeString = request.getParameter("code");
		try {
			code = Integer.valueOf(codeString);
		} catch (NumberFormatException e) {
			code = null;
		}
	}

	public Integer getCode() {
		return code;
	}

}
