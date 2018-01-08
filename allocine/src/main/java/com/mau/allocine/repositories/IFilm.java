package com.mau.allocine.repositories;

import java.util.List;

import com.mau.allocine.metier.Film;

public interface IFilm {
	List<Film> findAll();
	
	Film save(Film f);

}
