import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';
import { MangaRepositoryService } from '../../services/manga-repository.service';
import { Key } from 'selenium-webdriver';

@Component({
  selector: 'app-search-manga',
  templateUrl: './search-manga.component.html',
  styleUrls: ['./search-manga.component.css']
})
export class SearchMangaComponent implements OnInit {

  public searchTerm: string; 
  // construire un tableau de 0 a 5
  public ratingRange: number[] = Array.from({length:5}, (value, key) => key + 1);
  private searchTermSubject: Subject<string>;
  public currentRating: number;

  constructor(private _managaRepository: MangaRepositoryService) {
    this.searchTermSubject = new Subject();
   }

  ngOnInit() {
    this.currentRating = 0; // pas de filtrage par rating, donc 0 revient à il y a pas de filtrage
    this.searchTerm = "filtrer";
    this.searchTermSubject.asObservable()
                          .debounceTime(2000)
                          .subscribe(newTerm => this._managaRepository.changeSearch(newTerm));
  }
   changeTerm(newvalue) : void{

    this.searchTermSubject.next(newvalue);
      //console.log(newvalue);
     this.searchTerm = newvalue
   }

   setRatingMin(rating : number) : boolean{
      console.log(" change rating: " + rating);
      if(rating !=this.currentRating){
        this._managaRepository.setfilterByRatingMin(rating);
      }
      this.currentRating = rating;
      return false// cela evite de suivre le lien du href dans le search.html
   }
}
