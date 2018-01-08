import { Component, OnInit } from '@angular/core';
import { Tache } from '../../metier/tache';
import { ActivatedRoute, Router } from '@angular/router';

import { TacheRepositoryService } from '../../services/tache-repository.service';

@Component({
  selector: 'app-edit-tache',
  templateUrl: './edit-tache.component.html',
  styleUrls: ['./edit-tache.component.css']
})
export class EditTacheComponent implements OnInit {

  public editTache: Tache;
  constructor(private activeRoute: ActivatedRoute,
              private router: Router,
              private tacherepository :TacheRepositoryService) {

               }

  ngOnInit() {

    this.editTache = new Tache(0, "tp", "tp de java",2, new Date(), new Date(), "formation",false);
    this.activeRoute.params
        .subscribe(params => {
          // le parametre ":id" dans la route est disponible 
          let id =  params["id"];
          console.log("param id recu = " + id);
        if (id != 0) {
          this.tacherepository.findTache(id)
              .then(t => {
                // quand je recois le produit en réponse
                // le mettre dans le formulaire
                this.editTache = t;
              });
          };
        });
  }

  sauverTache(): void{
    console.log("sauvegarder tache...");
    console.log(this.editTache);
    this.tacherepository.saveTache(this.editTache)
                        .then(t => {
                          console.log("sauvegarde ok: " + t.id);
                          this.router.navigateByUrl('/liste')
                        })
                        .catch(err =>{
                          console.log("erreur a la sauvegarde: " + err);
                      });

  }

}
