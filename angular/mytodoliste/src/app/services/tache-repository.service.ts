import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Tache } from '../metier/tache';

@Injectable()
export class TacheRepositoryService {
   
  private tacheSubject : BehaviorSubject<Tache[]>;
  private searchLibelle: string;

  constructor( private _http: HttpClient) {
    this.searchLibelle = "";
    this.tacheSubject = new BehaviorSubject([]);
   }

   // pour modifier le filtrage de la listes des taches en fonction du titre
   public changeSearch(searchTerm : string) : void{
    this.searchLibelle = searchTerm;
    this.refreshListe();
  }

   public listeTacheObservable() : Observable<Tache[]>{
    return this.tacheSubject.asObservable();
 }

    public refreshListe(): void {

      let url = "http://localhost:8080/mytodoliste/taches";
      if(this.searchLibelle != ""){
        url+=`/search/${this.searchLibelle}`;// attention c'est bien avec les bas de cote
      }
      this._http.get<Tache[]>(url)
                .toPromise()
                .then(taches => this.tacheSubject.next(taches))
    }

    public saveTache(tache : Tache) : Promise<Tache>{
      const httpoptions = {
        headers: new HttpHeaders({'Content-Type' : 'application/json'})
      };
      if (tache.id == 0){
        // il s'agit d'une insertion
        return this._http.post<Tache>("http://localhost:8080/mytodoliste/taches", tache, httpoptions).toPromise();
      }
      else{
        // mise a jour
        return this._http.put<Tache>("http://localhost:8080/mytodoliste/taches", tache, httpoptions).toPromise();
      }
    
  }

    // trouver le tache avec l'id voulu
    findTache(id : number) : Promise<Tache> {
      // ici, on renvoie une promesse qui contiendra le tache renvoyé
      let url = `http://localhost:8080/mytodoliste/taches/${id}`;
      return this._http.get<Tache>(url).toPromise();
    }


    deleteTache(id : number) : Promise<Tache> {
      let url = `http://localhost:8080/mytodoliste/taches/${id}`;
      return this._http.delete<Tache>(url).toPromise();
    }

}
