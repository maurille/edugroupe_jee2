import { Component, OnInit } from '@angular/core';
import{ActivatedRoute, Router} from '@angular/router';
import { Manga } from '../../metier/manga';
import { MangaRepositoryService } from '../../services/manga-repository.service';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-edit-manga',
  templateUrl: './edit-manga.component.html',
  styleUrls: ['./edit-manga.component.css']
})
export class EditMangaComponent implements OnInit {

  public editManga: Manga;

  constructor(private activeRoute: ActivatedRoute,
              private router: Router,
               private mangarepository : MangaRepositoryService) {
                
               }

  ngOnInit() {

    this.editManga = new Manga(0, "", "bill", new Date(), "comedie", 3);
    this.activeRoute.params
        .subscribe(params => {
          // le parametre ":id" dans la route est disponible 
          let id =  params["id"];
          console.log("param id recu = " + id);
        if (id != 0) {
          this.mangarepository.findManga(id)
              .then(m => {
                // quand je recois le produit en réponse
                // le mettre dans le formulaire
                this.editManga = m;
              });
          };
        });
  }

  sauverTache(monform : NgForm): void{
    console.log("sauvegarde tache...");
    console.log("valider" + monform.valid);
    console.log("dirty" + monform.dirty);
    console.log(this.editManga);
    this.mangarepository.saveManga(this.editManga)
                        .then(m => {
                          console.log("sauvegarde ok: " + m.id);
                          this.router.navigateByUrl('/liste')
                        })
                        .catch(err =>{
                          console.log("erreur a la sauvegarde: " + err);
                      });


   // this.router.navigateByUrl('/liste');
  }

}
