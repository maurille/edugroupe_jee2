package com.mau.magamaniaboot.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.mau.magamaniaboot.metier.Manga;
import com.mau.magamaniaboot.repositories.MangaRepository;


@Controller
@RequestMapping(value="/")
public class IndexController {
	
	@Autowired
	private MangaRepository mangaRepository;
	
	
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToIndex() {
		return "home";
	}*/
	
	@RequestMapping(value="/mangas", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public List<Manga> listeManga(){
		ArrayList<Manga> data = new ArrayList<Manga>();
		mangaRepository.findAll().forEach(data::add);
		return data;
	 }

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/pmangas", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Page<Manga> listePManga(@PageableDefault(page=0, size=5)Pageable page,
			 @RequestParam("ratingMinimum") Optional<Integer> ratingMinimum){
		if(ratingMinimum.isPresent())
		return mangaRepository.findByRatingGreaterThanEqual(ratingMinimum.get(), page);
		else
			return mangaRepository.findAll(page);

	 }

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/mangas/search/{search:.+}", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public List<Manga> searchManga(@PathVariable("search") String search){
		return mangaRepository.findByTitreContaining(search);
	 }
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/pmangas/search/{search:.+}", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Page<Manga> searchPManga(@PathVariable("search") String search,
			 @RequestParam("ratingMinimum") Optional<Integer> ratingMinimum,
			 @PageableDefault(page=0, size=5) Pageable page){
		if(ratingMinimum.isPresent())
			return mangaRepository.findByTitreContainingAndRatingGreaterThanEqual(search, ratingMinimum.get(), page);
		else
			return mangaRepository.findByTitreContaining(search, page);
	 }
	
	/*
	 * 	@RequestMapping(value="/pmangas/search/{search:.+}", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Page<Manga> searchPManga(@PathVariable("search") String search, @PageableDefault(page=0, size=5) Pageable page){
		return mangaRepository.findByTitreContaining(search, page);
	 }
	 */
	
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/mangas", method=RequestMethod.POST, 
					produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Manga saveManga(@RequestBody Manga manga) {
		return mangaRepository.save(manga);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/mangas/{id:[0-9]+}", 
					method=RequestMethod.GET,
					produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	 public Manga findManga(@PathVariable("id") Long id){
			Manga m = mangaRepository.findOne(id);
			if(m ==null)
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "manga inconnu");
			return m;
	 }
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/mangas", method=RequestMethod.PUT, 
			produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public Manga updateManga(@RequestBody Manga manga) {
		Manga m = mangaRepository.findOne(manga.getId());
		if(m ==null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "manga inconnu");
return mangaRepository.save(manga);
}

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/mangas/{id:[0-9]+}", 
			method=RequestMethod.DELETE,
			produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public Manga deleteManga(@PathVariable("id") Long id){
	Manga m = mangaRepository.findOne(id);
	if(m ==null)
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "manga inconnu");
	mangaRepository.delete(m);
	return m;
}

}