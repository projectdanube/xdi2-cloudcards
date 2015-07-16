package xdi2.cloudcard.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.cloudcard.model.Card;
import xdi2.cloudcard.service.CardService;
import xdi2.core.exceptions.Xdi2ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class CloudCardController {
	private static final Logger log = LoggerFactory.getLogger(CloudCardController.class);

	@Autowired
	CardService cardService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String prepareSearchCard(Model model) throws Xdi2ClientException, JsonProcessingException {
		return "search";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String searchCard(Model model) throws Xdi2ClientException, JsonProcessingException {
		return "search";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getCard(@PathVariable String env, @PathVariable String id, Model model) throws Xdi2ClientException, JsonProcessingException {
		Card card = cardService.getCard(id);

		ObjectMapper mapper = new ObjectMapper();
		String jsonCard = mapper.writeValueAsString(card);

		model.addAttribute("card", jsonCard);

		return "index";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String getPrivateCard(
			@PathVariable String id,
			@RequestParam("xdiMessagingResponse") String xdiMessagingResponse,
			Model model) throws Xdi2ParseException, IOException, Xdi2ClientException {


		if (xdiMessagingResponse.contains("<$false>")) {
			model.addAttribute("errorMsg", "Something went wrong while connecting with the card.");
			return "index";
		}

		Card card = cardService.getCard(id, xdiMessagingResponse);

		if (card == null) {
			model.addAttribute("errorMsg", "Something went wrong while connecting with the card.");
			return "index";
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonCard = mapper.writeValueAsString(card);

		model.addAttribute("card", jsonCard);

		return "index";

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleException(Exception e) {
		log.error("Unexpected exception caught: " + e, e);
		
		ModelAndView model = new ModelAndView("index");
		model.addObject("errorMsg", "Something went wrong: " + e.getMessage());

		return model;
	}

}
