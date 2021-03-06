import { Component, OnInit, TemplateRef } from '@angular/core';
import { ImageRepositoryService } from '../../services/image-repository.service';

import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';
import { Image } from '../../models/image';
import { OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';

import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { Lightbox } from 'angular2-lightbox';
import { TruncatePipe } from "angular-pipes/src/string/truncate.pipe";
import { BytesPipe } from "angular-pipes/src/math/bytes.pipe";
import { AuthManagerService } from '../../services/auth-manager.service';
import { AlertManagerService } from '../../services/alert-manager.service';
import { Tag } from '../../models/tag';



@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.css']
})
export class ImageListComponent implements OnInit, OnDestroy {
  modalRef: BsModalRef;

  private souscription : Subscription;
  public images : Subject<Image[]>;
  public totalItems : number = 0;
  public currentPage : number = 1;
  public idToDelete : number = 0;
  public gallerieLiens : any[] = [];

  constructor(private imageRepository : ImageRepositoryService,
              private modalService : BsModalService,
              private lightBox : Lightbox,
              private authManager: AuthManagerService,
              private alertManager: AlertManagerService) { }

  public getImageThumbUrl(id: number) : string {
    return this.imageRepository.getImageThumbUrl(id);
  }
  public getImageUrl(id: number) : string {
    return this.imageRepository.getImageUrl(id);
  }
  public getImagePopovertext(image : Image) : string {
    if( image.tags == null || image.tags.length == 0) {
      return "aucun tags";
    }
    else {
      return "tags: " + image.tags.map(t => t.libelle).join(",");
    }
  }

  public pageChanged(event : any) : void {
    this.imageRepository.setNoPage(event.page - 1);
  }

  // ai-je le droit de supprimer ?
  public canDelete() : boolean {
    //console.log("canDelete " + this.authManager.isRoleActive("ROLE_ADMIN"));
    return true //this.authManager.isRoleActive("ROLE_ADMIN") || this.authManager.isRoleActive("ROLE_USER");
  }

  // affiche le dialogue
  public deleteImage(id : number, deleteTemplate : TemplateRef<any>) : void {
    console.log("effacement image no " + id + " demandé");
    this.idToDelete = id; // on memorise l'id de l'image a effacer
    this.modalRef = this.modalService.show(deleteTemplate);
  }

  // appelé si on clique sur ok dans le dialogue du dessus
  public confirmDelete() : void {
    this.modalRef.hide();
    console.log("effacement image no " + this.idToDelete + " confirmé");
    this.imageRepository.deleteImages([this.idToDelete]);
  }


  public openGallerie(image: Image) : void {
    /*let album = [];
    album.push({
      src: this.imageRepository.getImageUrl(image.id),
      caption: image.fileName
    });*/
    // quelle est la position de l'image cliqué dans l'album
    let position = this.gallerieLiens.findIndex(imglien => imglien.id == image.id);
    // ouvrir l'album positionné sur la bonne image
    this.lightBox.open(this.gallerieLiens,
                       position,
                      {
                        fadeDuration: 0.2,
                        resizeDuration: 0.2,
                        showImageNumberLabel : true,
                        wrapAround: true
                      });
  }

  public selectNewTag(tag : Tag): void{
    this.imageRepository.addSelectedTag(tag);
  }

 public  unSelectTag(tag : Tag) :void{
  this.imageRepository.removeSelectedTag(tag);

 }
  ngOnInit() {
    this.images = new Subject();
    this.souscription = this.imageRepository.listeImageAsObservable()
                            .subscribe(p =>  {
                              // mettre a jour liens pour lightbox
                              this.gallerieLiens = [];
                              p.content.forEach(img => {
                                this.gallerieLiens.push({
                                  id : img.id,
                                  src: this.imageRepository.getImageUrl(img.id),
                                  caption : img.fileName
                                });
                              });
                              // publier les images pour le ngFor
                              this.images.next(p.content);
                              this.totalItems = p.totalElements;
                              this.currentPage = p.number + 1;
                            });
    this.imageRepository.refreshListe();
  }

  ngOnDestroy(): void {
    this.souscription.unsubscribe();
  }

}
