package com.aotal.applygatetest;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

  private static final Logger logger = LoggerFactory.getLogger(WebController.class);

  private RestTemplate restTemplate = new RestTemplate();
  
  @RequestMapping("/tenant/{tenant}/openSesame/{job}")
  public String showOpen(Model model, @PathVariable String tenant, @PathVariable int job, @RequestParam String relayPage) {
	  model.addAttribute("tenant", tenant);
	  model.addAttribute("job", job);
	  model.addAttribute("relayPage", relayPage);
	  return "openSesame";
  }

  // holy security alert Batman - passing details directly within the URL like this allows an attacker candidate to forge
  // a url to satisfy any job at any tenant, and then redirect to any page on the internet! Add a signature at least, in anything but a demo like this.
  @RequestMapping("/tenant/{tenant}/showQualifyPage/{job}")
	  public String showQualify(Model model, @PathVariable String tenant, @PathVariable int job, @RequestParam String relayPage) {
	  
	  // get a link to the satisfy page for this job
	  String api = "https://applygatedemo.tazzy.io/t/" + tenant
			  	+ "/devs/tas/actions/byCandidate/anonymous/jobs/" + job
			  	+ "/satisfyLink/qualify" // qualify is the name of our button (the one which will be satisfied)
			  	+ "?relayPage=" + URLEncoder.encode(relayPage)
			  	+ "&message=" + URLEncoder.encode("Congratulations! You can now apply.");

	  logger.info("fetching satisfy link via API call to: " + api);

	  // now make the API call to get the link
	  HttpHeaders headers = new HttpHeaders();
	  headers.set("tazzy-secret", APIController.SECRET);
	  HttpEntity entity = new HttpEntity(headers); // attaching the "tazzy-secret" request header
	  ResponseEntity<String> response = restTemplate.exchange(api, HttpMethod.GET, entity, String.class);
	  String satisfyLink = response.getBody();

	  logger.info("redirecting candidate to satisfy link: " + satisfyLink);
	  
      return "redirect:" + satisfyLink; 	  // redirect candidate to the satisfy link, where they can log in and unlock the gate
  }

  
}

