import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

import { Image } from '../models/Image';
import { Page } from '../models/page';

import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Promise } from 'q';
import { Tag } from 'app/models/tag';
import { Subject } from 'rxjs/Subject';



@Injectable()
export class ImageRepositoryService {
  // la liste des image renvoyé par le srveur sera pubklié ici
private ImagesSubject:  BehaviorSubject<Page<Image>>
private noPage : number;
private taillePage : number;
private selectedTags : Tag[];// tag selectionnée
private selectedTagsSubject : BehaviorSubject<Tag[]>;
private baseUrlApi: string = "http://localhost:8080/api/images";
private baseUrlextendedApi: string = "http://localhost:8080/extendedapi/image";


  constructor(private _http: HttpClient) {

    // initialisation des images
    this.noPage = 0;
    this.taillePage = 12;
    this.ImagesSubject = new BehaviorSubject(Page.emptyPage<Image>());
     // initialisation des tags
    this.selectedTags = [];
    this.selectedTagsSubject =new BehaviorSubject(this.selectedTags);
   }

   public addSelectedTag(tag : Tag){
      if(this.selectedTags.findIndex(t => t.id == tag.id) == -1){
        // tag non present , on peut l'ajouter
        this.selectedTags.push(tag);
        // prevenir ceux ecoutant que la liste des tags selectionnés a changé
        this.selectedTagsSubject.next(this.selectedTags);
        //je rafraichi donc la liste des image
        this.refreshList();
      }
   }


   public removeSelectedTags(tag : Tag){
    let index = this.selectedTags.findIndex(t => t.id == tag.id);
    if(index != -1){
      // rretrait du tag du tableau (position , nombre)
      this.selectedTags.splice(index, 1);
      this.selectedTagsSubject.next(this.selectedTags);
      this.refreshList();
    }
   }

   
public selectedtagsAsObservable() : Observable<Tag[]>{
  return this.selectedTagsSubject.asObservable();
}
   
   public setNoPage(no : number) : void {
    this.noPage = no;
    this.refreshList();
  }

   public listeImagesObservable() : Observable<Page<Image>> {
    return this.ImagesSubject.asObservable();
 }


public deleteImages(ids: number[]) : void{
    // on veut avoir "4,4,5,6" si on entre [4,4,5,6]
    let ids_string =ids.join(",");
    let urlparams : HttpParams = new HttpParams();
    urlparams = urlparams.set("imagesId", ids_string);
    // appeler delete sur le serveur avec les ids
    this._http.delete<Map<string , number>>(`${this.baseUrlextendedApi}/delete`, {params: urlparams})
              .toPromise()
              .then(result => { 
                console.log(result);
                this.refreshList();
              });

}
 


   refreshList() : void{

    let urlparams : HttpParams = new HttpParams();
    // gestion de la pagination
      urlparams = urlparams.set("page", "" + this.noPage);
      // gestion du filtrage par tags
    if(this.selectedTags.length > 0){
      // liste des ids de tags separé par des virgules dans le parametre 
      urlparams = urlparams.set("tagsId",
        this.selectedTags.map(t =>"" + t.id ).join(","));
    }

    this._http.get<Page<Image>>(`${this.baseUrlextendedApi}/findbytagfull`, {params: urlparams})
              .toPromise()
                .then(images => this.ImagesSubject.next(images))
                .catch(e => console.log("Pas d'image reçu"));
   }




   public getImageThumbUrl(id : number): string{
     return `${this.baseUrlextendedApi}/downloadthumb/${id}`;
   }

   public getImageUrl(id : number): string{
    return `${this.baseUrlextendedApi}/download/${id}`;
  }

  public getUploadurl() :  string{
    return `${this.baseUrlextendedApi}/upload`;
  }
  
}
