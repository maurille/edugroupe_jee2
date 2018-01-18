import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { HttpEvent, HttpHeaderResponse, HttpProgressEvent, HttpResponse, HttpUserEvent, HttpSentEvent, HttpErrorResponse } from '@angular/common/http';
import { AuthManagerService } from './auth-manager.service';

import'rxjs/add/operator/catch';
import'rxjs/add/observable/throw';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor{

  constructor(private autManager: AuthManagerService, private router : Router) { }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpSentEvent|HttpHeaderResponse|HttpProgressEvent|HttpResponse<any>|HttpUserEvent<any>>{
    
    // on est avant envoie de requette vers le server
    //console.log("requette interceptée: " + req.url);
    if(this.autManager.isloggedIn()){
      req= req.clone({setHeaders: {
        Authorization: `Basic ${this.autManager.getCredentials()}`
      }});
    }
    // ici c'est l'envoie de la requette à la suite
    return next.handle(req)
                .catch((error, caught) => {
                  console.log(" erreur de la reponse");
                  // console.log(error);
                  if(error instanceof HttpErrorResponse){
                    let resp : HttpErrorResponse = error;
                    if(resp.status == 401){
                      // besoin d'authentifiation aller a la page du login
                      this.router.navigateByUrl("/login");
                    }
                  }
                  // retransmission de l'erreur
                   return Observable.throw(error);

                });
    
    /*pipe(evt => {
      console.log(" traitmnet reponse");
      console.log(evt);
      return evt;

    });*/
  }

}
