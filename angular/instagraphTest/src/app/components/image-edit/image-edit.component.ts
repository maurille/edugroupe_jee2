import { Component, OnInit } from '@angular/core';
import { ImageRepositoryService } from '../../services/image-repository.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Image } from '../../models/image';
import { AlertManagerService } from '../../services/alert-manager.service';
import { Tag } from '../../models/tag';
import { TagRepositoryService } from '../../services/tag-repository.service';

@Component({
  selector: 'app-image-edit',
  templateUrl: './image-edit.component.html',
  styleUrls: ['./image-edit.component.css']
})
export class ImageEditComponent implements OnInit {

  public editedImage : any|Image;

  constructor(private imageRepository: ImageRepositoryService, private router : Router,
              private activeRoute : ActivatedRoute,
              private tagRepo : TagRepositoryService,
            private alertManager: AlertManagerService) { }

  ngOnInit() {
    this.editedImage = {};
    this.activeRoute.params.subscribe(params => {
      let id = Number(params["id"]);
      console.log("id image =" + id);
      this.imageRepository.findImage(id).then(img => this.editedImage = img).catch( err => {
        this.alertManager.handleErrorResponse(err);
        this.router.navigateByUrl("/liste");
      })
    }) 
  }

    public selectNewTag(tag : Tag): void{
      console.log("ajout tag" + tag.libelle + " a l'mage");
      this.tagRepo.addTags([tag], [this.editedImage]).then(tags => {
        this.alertManager.handleMessage("success", `image '${tags[0].libelle}' added`);               
      }).catch( err => {
        this.alertManager.handleErrorResponse(err);
      });
    }

  public  unSelectTag(tag : Tag) :void{
    console.log("retrait tag" + tag.libelle + " a l'mage");
  }


  public saveImage() : void {
    this.imageRepository.updateImage(this.editedImage)
        .then( img => {
              this.alertManager.handleMessage("success", `image '${img.name}' saved`);
              this.router.navigateByUrl("/liste");
            })
        .catch( err => {
          this.alertManager.handleErrorResponse(err);
        });
  }
}


