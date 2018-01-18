import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { promise } from 'selenium-webdriver';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { Utilisateur } from '../metier/utilisateur';

@Injectable()
export class AuthManagerService {

  private baseUrlApi: string = "http://localhost:8080/extendedapi/auth";
  private currentUser: Utilisateur;
  private utilisateurSubject: Subject<[boolean, Utilisateur]>;

  public getCurrentUser(): Utilisateur{
    return this.currentUser;
  }

  public isloggedIn() : boolean{
    if(this.currentUser == null)
      return false;
    else
      return true;;
  }

  public getCredentials():string{
    // generation de la valeur des credentials pour authentification
    return window.btoa(this.currentUser.username + ":" + this.currentUser.password);
  }

  public setCurrentUser(utilisateur: Utilisateur) : void{
    this.currentUser = utilisateur;
    // publication de nouveau user qui est loggé
    this.utilisateurSubject.next([true, this.currentUser])
  }

  public logOut(): void{
    this.currentUser = null;
    // publication du fait qu'il y a plus d'utilisateur loggé
    this.utilisateurSubject.next([false, null]);
  }
  constructor() { 
    this.currentUser = null;
    this.utilisateurSubject = new Subject<[boolean, Utilisateur]>();
  }

  public getUtilisateurAsObservable() : Observable<[boolean, Utilisateur]>{
    return this.utilisateurSubject.asObservable();
  }

}
