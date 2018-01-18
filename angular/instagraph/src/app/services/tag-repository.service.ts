import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

import { Tag } from '../models/Tag';
import { Page } from '../models/page';

import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class TagRepositoryService {
  // la liste des image renvoyé par le srveur sera pubklié ici
private tagsSubject:  BehaviorSubject<Page<Tag>>
private noPage : number;
private taillePage : number;

private baseUrlApi: string = "http://localhost:8080/api/tags";
private baseUrlextendedApi: string = "http://localhost:8080/extendedapi/tag";


  constructor(private _http: HttpClient) {
    this.noPage = 0;
    this.taillePage = 12;
    this.tagsSubject = new BehaviorSubject(Page.emptyPage<Tag>());
   }

   public setNoPage(no : number) : void {
    this.noPage = no;
    this.refreshList();
  }

   public listeImagesObservable() : Observable<Page<Tag>> {
    return this.tagsSubject.asObservable();
 }


   refreshList() : void{

    let urlparams : HttpParams = new HttpParams();
      urlparams = urlparams.set("page", "" + this.noPage);


    this._http.get<Page<Tag>>(`${this.baseUrlextendedApi}/liste`, {params: urlparams})
              .toPromise()
                .then(t => this.tagsSubject.next(t))
                .catch(e => console.log("Pas de tags reçu"));
   }
}
