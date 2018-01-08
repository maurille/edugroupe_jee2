package com.mau.mytodoliste.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import com.mau.mytodoliste.metier.Tache;
import com.mau.mytodoliste.repositories.TacheRepository;




@Controller
@RequestMapping(value="/")
public class IndexController {
	@Autowired
	private TacheRepository tacheRepository;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToIndex() {
		return "bonjour";
	}
	
	@RequestMapping(value="/taches", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public List<Tache> listeTache(){
		ArrayList<Tache> datas = new ArrayList<Tache>();
		tacheRepository.findAll().forEach(datas::add);
		return datas;
	 }
	
	@RequestMapping(value="/ptaches", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Page<Tache> listePTache(@PageableDefault(page=0, size=5)Pageable page,
			 						@RequestParam("prioriteMinimum") Optional<Integer> prioriteMinimum){
		if(prioriteMinimum.isPresent())
		return tacheRepository.findByPrioriteGreaterThanEqual(prioriteMinimum.get(), page);
		else
			return tacheRepository.findAll(page);

	 }
	

	
	@RequestMapping(value="/taches/search/{search:.+}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public List<Tache> searchTache(@PathVariable("search") String search){
		return tacheRepository.findByLibelleContaining(search);
	 }
	
	@RequestMapping(value="/ptaches/search/{search:.+}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Page<Tache> searchPManga(@PathVariable("search") String search,
			 @RequestParam("prioriteMinimum") Optional<Integer> prioriteMinimum,
			 @PageableDefault(page=0, size=5) Pageable page){
		if(prioriteMinimum.isPresent())
			return tacheRepository.findByLibelleContainingAndPrioriteGreaterThanEqual(search, prioriteMinimum.get(), page);
		else
			return tacheRepository.findByLibelleContaining(search, page);
	 }
	
/*
	@RequestMapping(value="/taches/priorite/{priorite:.+}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public List<Tache> findByPrioriteGreaterThan(@PathVariable("priorite") int priorite){
		return tacheRepository.findByPrioriteGreaterThan(priorite);
	 }*/
	
	
	@RequestMapping(value="/taches", method=RequestMethod.POST, 
					produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Tache saveTache(@RequestBody Tache tache) {
		return tacheRepository.save(tache);
	}
	
	@RequestMapping(value="/taches/{id:[0-9]}", 
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Tache findTache(@PathVariable("id") int id){
		Tache t = tacheRepository.findOne(id);
			if(t ==null)
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tache inconnu");
			return t;
	 }
	
	@RequestMapping(value="/taches", method=RequestMethod.PUT, 
			produces=MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public Tache updateTache(@RequestBody Tache tache) {
		Tache t = tacheRepository.findOne(tache.getId());
		if(t ==null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tache inconnu");
return tacheRepository.save(tache);
}


	@RequestMapping(value="/taches/{id:[0-9]}", 
			method=RequestMethod.DELETE,
			produces=MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public Tache deleteTache(@PathVariable("id") int id){
		Tache t = tacheRepository.findOne(id);
	if(t ==null)
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tache inconnu");
	tacheRepository.delete(t);
	return t;
}

}