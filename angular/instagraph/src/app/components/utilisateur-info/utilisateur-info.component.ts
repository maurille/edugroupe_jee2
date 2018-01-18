import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthManagerService } from 'app/services/auth-manager.service';
import { Utilisateur } from 'app/models/utilisateur';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';

@Component({
  selector: 'app-utilisateur-info',
  templateUrl: './utilisateur-info.component.html',
  styleUrls: ['./utilisateur-info.component.css']
})
export class UtilisateurInfoComponent implements OnInit {

  public isLoggedIn : boolean;
  public currentUser : Utilisateur;
  public currentUserSouscription: Subscription;

  constructor(private authManager: AuthManagerService, private router : Router) { }

  ngOnInit() {
    this.isLoggedIn = this.authManager.isloggedIn();
    this.currentUser = this.authManager.getCurrentUser();
    this.currentUserSouscription = this.authManager.getUtilisateurAsObservable()
                                                   .subscribe( u => {
                                                                      this.isLoggedIn = u[0];
                                                                      this.currentUser = u[1];
                                                                     });
   }

  ngOnDestroy(): void{
    this.currentUserSouscription.unsubscribe();
  }

  public logmeout(): boolean{
    this.authManager.logOut();
    this.router.navigateByUrl("/login");
    return false;
  }

}
