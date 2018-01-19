import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Utilisateur } from 'app/models/utilisateur';
import { promise } from 'selenium-webdriver';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';

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
      return true;
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

  public isRoleActive(roleName : string) : boolean{
    if (this.currentUser == null || this.currentUser.roles == null)
      return false;
    if(this.currentUser.roles.findIndex(r => r.roleName == roleName) != -1){
      return true;
    }else
      return false;

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

  /*public logMeIn(username : string, password : string) : void{
    let newuser = new Utilisateur(username, password, true);
    this._http.post<Utilisateur>(`${this.baseUrlApi}/login`,newuser)
              .subscribe(u => {  
                                  // l'utilisateur nouvellement loggédevient l'utilisateur courrant
                                  this.currentUser = u;
                                  //on previent du changement
                                  this.utilisateurSubject.next(u);
                              });
  }*/

}
