package com.mau.instagraph.web;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.mau.instagraph.metier.CompositeOwnerKey;
import com.mau.instagraph.metier.Image;
import com.mau.instagraph.metier.Owner;
import com.mau.instagraph.metier.Utilisateur;
import com.mau.instagraph.metier.projections.ImageWithTags;
import com.mau.instagraph.repositories.ImageRepository;
import com.mau.instagraph.repositories.OwnerRepository;
import com.mau.instagraph.repositories.UtilisateurRepository;
import com.mau.instagraph.util.FileStorageManager;



@Controller
@RequestMapping("/extendedapi/image")
public class ImageController {
	
	
	@Autowired
	private final ProjectionFactory projectionFactory;
	public ImageController(ProjectionFactory projectionFactory) {
		this.projectionFactory = projectionFactory;
	}
	
	
	private static Logger log = LogManager.getLogger(ImageController.class);
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private FileStorageManager fileStorageManager;
	
//**************methode findbytag***********************************************	

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/findbytag",
	method=RequestMethod.GET,
	produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER')")
	public Page<Image> findByTags(@RequestParam("tagsId") Optional<List<Integer>> tagsId, 
								 @PageableDefault(page=0, size=12) Pageable page){
		
		
		if(tagsId.isPresent())
			log.info("tagsId =" + tagsId.get().toString());
		else
			log.info("pas de tags en parametre ");
		
		return imageRepository.findAll(page);
	}
	//*****************findbytags complete**************************************
	

		@CrossOrigin(origins="http://localhost:4200")
		@RequestMapping(value="/findbytagfull",
		method=RequestMethod.GET,
		produces=MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		@PreAuthorize("hasRole('ROLE_USER')")
		public Page<ImageWithTags> findByTagsFull(@RequestParam("tagsId") Optional<List<Integer>> tagsId, 
									 @PageableDefault(page=0, size=12) Pageable page){
			
			
			if(tagsId.isPresent()) {
				log.info("tagsId =" + tagsId.get().toString());
				return imageRepository.searchWithTags(tagsId.get(), null, page)
									  .map(img -> projectionFactory.createProjection(ImageWithTags.class, img));
				}
			
			
			else {
				log.info("pas de tags en parametre ");
				return imageRepository
					.findAll(page)
					.map(img -> projectionFactory.createProjection(ImageWithTags.class, img));
			}
			
			
		}
		
		
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/upload", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	// ce nom file est celui mis dans sopui, pour le post, request/ value, qui est le nom du lien de notre chaton
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER')")
	public Image upload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Authentication authentication ) {
		
		log.info("current user : " + authentication.getName());
		log.info("file name :" + file.getOriginalFilename());
		log.info("content type :" + file.getContentType());
		
		try {
				Image img = new  Image(0,
						 file.getOriginalFilename(),
						  "",
						  LocalDateTime.now(),
						  file.getOriginalFilename(),
						  file.getContentType(),
						  file.getSize(),
						  0,
						  0,
						  DigestUtils.sha1Hex(file.getInputStream()),  // somme de controle du fichier,
						  "",
						  "");
		
				imageRepository.saveImageFile(img, file.getInputStream());
				// le fichiere est suavegarder et img contien le storagID correspondant
				imageRepository.save(img);
				// ligne inserer dans la base
				
				
				Utilisateur u = utilisateurRepository.findByUsername(authentication.getName());
				Owner o = new Owner(new CompositeOwnerKey(img, u));
				ownerRepository.save(o);
				return img;
				
			
			} catch(IOException e) {
					throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "erreur a la sauvegarde");
			}
	}

	
	
	@RequestMapping(value="/download/{id:[0-9]+}", method=RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<FileSystemResource> imageData(@PathVariable("id") long id) {
		Image img = imageRepository.findOne(id);
		if (img == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "image inconnue");
		// on recupere le fichier correspondant
		Optional<File> fichier = imageRepository.getImageFile(img.getStorageId());
		if (!fichier.isPresent())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "fichier image introuvable");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(img.getContentType()));
		headers.setContentLength(img.getFileSize());
		headers.setContentDispositionFormData("attachment", img.getFileName());
		ResponseEntity<FileSystemResource> re =
				new ResponseEntity<FileSystemResource>(new FileSystemResource(fichier.get()),
													headers,
													HttpStatus.ACCEPTED);
		return re;
	}
	
	
	
	// cette methode est pout l'image en miniature
	@RequestMapping(value="/downloadthumb/{id:[0-9]+}", method=RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("permitAll")
	public ResponseEntity<FileSystemResource> imageDataThumb(@PathVariable("id") long id) {
		Image img = imageRepository.findOne(id);
		if (img == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "image inconnue");
		// on recupere le fichier correspondant
		Optional<File> fichier = imageRepository.getImageFile(img.getThumbStorageId());
		if (!fichier.isPresent())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "fichier image introuvable");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentLength(fichier.get().length());
		headers.setContentDispositionFormData("attachment", img.getFileName());
		ResponseEntity<FileSystemResource> re =
				new ResponseEntity<FileSystemResource>(new FileSystemResource(fichier.get()),
													headers,
													HttpStatus.ACCEPTED);
		return re;
	}
	
	
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/delete", 
	method=RequestMethod.DELETE,
	produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public Map<String, Object>  deleteImages(@RequestParam("imagesId") List<Long> imagesId, @AuthenticationPrincipal Authentication authentication){
		
			Map<String , Object> result = new HashMap<>();
			Iterable<Image> images =  imageRepository.findAll(imagesId);
			final Utilisateur u = utilisateurRepository.findByUsername(authentication.getName());
			//*************************Autorisé???*****************************************************
			
			if(!authentication.getAuthorities().stream()
											  .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) 
			{
				
				final OwnerRepository repo = this.ownerRepository;
				
				boolean ok = StreamSupport.stream(images.spliterator(), false).allMatch(img ->repo.exists(new CompositeOwnerKey(img,u)));
				
				if(!ok) {
					throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "vous ne pouvez supprimer ces images");
				}
			}
			
			
			for(Image img : images) {
				//Owner o = ownerRepository.findOne(new CompositeOwnerKey(img,u));
				List<Owner> o = ownerRepository.findByClef_ImageId(img.getId());
				if(!o.isEmpty())
					ownerRepository.delete(o);
				imageRepository.delete(img);
				
			}
			// efface les images dans la base
			//imageRepository.delete(images);
			int nbImageToDelete =0;
			int ndFilesDeleted = 0;
			
			// efface les fichier images correspondant 
			for(Image img : images) {
				nbImageToDelete++;
				if(imageRepository.deleteImageFile(img)) {
					ndFilesDeleted++;
				};
			}
			// retourne des information sur ce qui a été fait
			result.put("nbImageToDelete", nbImageToDelete);
			result.put("ndFileDeleted", ndFilesDeleted);
	
		return result;
	}

}
