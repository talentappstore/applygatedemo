package com.aotal.applygatetest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class APIController {

	// the public address of this app
	public static final String MOUNTPOINT = "https://applygatedemo.communityapps.talentappstore.com";
	public static final String SECRET = "6Q-B_IOGGJVdTZ7tbo7vgks2KEjsc7TfFzHOo8O1";

	private static final Logger logger = LoggerFactory.getLogger(APIController.class);

	@RequestMapping(value = "/t/{tenant}/tas/devs/tas/actions/byCandidate/job/possibles/byApp", method = RequestMethod.GET)
	public String getPossibles(@PathVariable String tenant) {
		
		logger.info("in GET /possibles");
		
		String ret = "[" +
		"  {" +
		"    \"actionName\": \"qualify\"," +
		"    \"availableToInternals\": true," +
		"    \"availableToExternals\": true," +
		"    \"mayRequireSetupFlag\": false," +
		"    \"semantics\": {" +
		"      \"purpose\": \"apply\"," +
		"      \"gateBehaviour\": {" +
		"         \"canReuseFlag\": false" +  // true is more common, but this is useful when demoing 
		"      }" +
		"    }" +
		"  }" +
		"]";
		
		return ret;
	}

	
	@RequestMapping(value = "/t/{tenant}/tas/devs/tas/actions/byCandidate/anonymous/jobs/{job}/byName/{action}/byApp", method = RequestMethod.GET)
	public String getAnonAction(@PathVariable String tenant, @PathVariable int job, @PathVariable String action,
			@RequestParam String relayPage) {
		
		logger.info("in GET /anonymous/action for tenant " + tenant);

		// the url of our quiz/etc. that the candidate will be redirected to when they click our button
	    String uri = MOUNTPOINT + "/tenant/" + tenant + "/openSesame/" + job + "?relayPage=" + relayPage;

		String ret = 
	    "{" +
		"\"name\": \"qualify\"," +
	    "  \"label\": \"Please let me apply!\"," +
	    "  \"uri\": \"" + uri + "\"," +
	    "  \"behavior\": \"redirect\"," +
	    "  \"textColor\": \"001122\"," +
	    "  \"backgroundColor\": \"eeccdd\"," +
	    "  \"disabled\": false" +
	    "}";
		
		return ret;
	}	
	

	
}
