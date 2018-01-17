package com.mau.firstBoot.respositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.mau.firstBoot.metier.SpaceShip;

public interface SpaceShipRepository extends PagingAndSortingRepository<SpaceShip, Integer>{

}
