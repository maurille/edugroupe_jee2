import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Tache } from '../../metier/tache';
import { TacheRepositoryService } from '../../services/tache-repository.service';

@Component({
  selector: 'app-liste-tache',
  templateUrl: './liste-tache.component.html',
  styleUrls: ['./liste-tache.component.css']
})
export class ListeTacheComponent implements OnInit {

  public taches : Observable<Tache[]>;

  constructor(private _tacheRepository : TacheRepositoryService) { }

  ngOnInit() {

    this.taches = this._tacheRepository.listeTacheObservable();

    this._tacheRepository.refreshListe();
  }

  public deleteTache(id : number): void{

    this._tacheRepository.deleteTache(id).then(t => {
              console.log("supprimer" + t.id);
              this._tacheRepository.refreshListe();
    });
  }

}
