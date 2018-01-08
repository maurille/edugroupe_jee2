package com.mau.allocine.web;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mau.allocine.metier.Film;
import com.mau.allocine.repositories.FilmDepot;
import com.mau.allocine.repositories.IFilm;


@Controller
@RequestMapping(value="/")
public class IndexController {
	@Autowired
	private IFilm filmDepot;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToIndex() {
		return "redirect:/Index";
	}

	
	@RequestMapping(value = "/Index", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "bonjour depuis spring 3 mvc");
		return "bonjour";

	}

	@RequestMapping(value = "/bonjour/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("bonjour");
		model.addObject("message", "bonjour " + name);

		return model;

	}
	@RequestMapping(value="/liste")
	public ModelAndView liste() {
		// on affichera via la page films.jsp
		ModelAndView model = new ModelAndView("films");// le nom du fichier jsp
		
		model.addObject("films", filmDepot.findAll()); //<c:forEach var="film" items="${films}">
		
		return model;	
	}
	
	@RequestMapping(value="/addFilm", method=RequestMethod.POST)
	public String addFilm(@RequestParam("titre") String titre,
						  @RequestParam("realisateur")  String realisateur,
						  @RequestParam("annee")  int annee,
						  @RequestParam("synopsis")  String synopsis) {
		
		Film f = new Film(0,titre, realisateur, annee, synopsis);
		filmDepot.save(f);
		return "redirect:/liste";
	}

}