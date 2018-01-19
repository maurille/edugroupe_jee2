import { Component, OnInit } from '@angular/core';
import { AuthManagerService } from 'app/services/auth-manager.service';
import { Subscription } from 'rxjs/Subscription';
import { HttpClient } from '@angular/common/http';
import { Utilisateur } from 'app/models/utilisateur';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public userlogin : any;
  private utilisateurSouscription : Subscription;
  private baseUrlApi: string = "http://localhost:8080/extendedapi/auth";


  constructor(private authManager : AuthManagerService, 
              private _http : HttpClient,
            private router : Router) {

   }

  ngOnInit() {
    this.userlogin ={};
   /*this.utilisateurSouscription = this.authManager.getUtilisateurAsObservable()
                                                  .subscribe(u => {
                                          console.log("je suis bien loggé avec " + u.username);
                                            });*/
  }

  ngOnDestroy(): void {
    //this.utilisateurSouscription.unsubscribe();
  }

  trylogin() : void{
    console.log(" tentative de login avec  "  + this.userlogin.username);
   // this.authManager.logMeIn(this.userlogin.username, this.userlogin.password);

    let newuser = new Utilisateur(this.userlogin.username, this.userlogin.password, true);
    this.authManager.setCurrentUser(newuser);
    this._http.post<Utilisateur>(`${this.baseUrlApi}/login`,newuser)
              .subscribe(u => {  
                                  // l'utilisateur nouvellement loggédevient l'utilisateur courrant
                                  //this.authManager.setCurrentUser(u);
                                  newuser.roles = u.roles;
                                   console.log(" je suis bien loggé avec " + u.username);
                                   this.router.navigateByUrl("/liste");
                              });

  }

}
