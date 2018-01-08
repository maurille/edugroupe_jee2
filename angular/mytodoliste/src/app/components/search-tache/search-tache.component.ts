import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';
import { TacheRepositoryService } from '../../services/tache-repository.service';

@Component({
  selector: 'app-search-tache',
  templateUrl: './search-tache.component.html',
  styleUrls: ['./search-tache.component.css']
})
export class SearchTacheComponent implements OnInit {


  public searchTerm: string 
  public searchPriorite: number;
  private searchTermSubject: Subject<string>;
 

  constructor(private _tacheRepository: TacheRepositoryService) {
    this.searchTermSubject = new Subject();
   
   }

  ngOnInit() {
    this.searchTerm = "";
    this.searchTermSubject.asObservable()
                          .debounceTime(1000)
                          .subscribe(newTerm => this._tacheRepository.changeSearch(newTerm));
  }
   changeTerm(newvalue) : void{

    this.searchTermSubject.next(newvalue);
     this.searchTerm = newvalue
   }

}
