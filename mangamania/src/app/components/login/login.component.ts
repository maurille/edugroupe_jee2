import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Utilisateur } from '../../metier/utilisateur';
import { AuthManagerService } from '../../services/auth-manager.service';

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
  }

  trylogin() : void{
    console.log(" tentative de login avec  "  + this.userlogin.username);
  

    let newuser = new Utilisateur(this.userlogin.username, this.userlogin.password, true);
    this.authManager.setCurrentUser(newuser);
    this._http.post<Utilisateur>(`${this.baseUrlApi}/login`,newuser)
              .subscribe(u => {  
                                  // l'utilisateur nouvellement loggédevient l'utilisateur courrant
                                  //this.authManager.setCurrentUser(u);
                                   console.log(" je suis bien loggé avec " + u.username);
                                   this.router.navigateByUrl("/liste");
                              });

  }

}
