import { Component, OnInit, OnDestroy } from '@angular/core';
import { ImageRepositoryService } from '../../services/image-repository.service';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { Image } from '../../models/image';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { TemplateRef } from '@angular/core/src/linker/template_ref';
import { Lightbox } from 'angular2-lightbox';
//import { TruncatePipe } from 'angular-pipes/src/string/truncate.pipe';
//import { BytesPipe } from 'angular-pipes/src/math/bytes.pipe';
 

@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.css']
})
export class ImageListComponent implements OnInit {
  modalRef: BsModalRef;

  private souscription : Subscription;
  public images : Subject<Image[]>;
  public totalItems : number = 0;
  public currentPage : number = 1;
  public idToDelete: number =0;
  public gallerieLiens : any[] = [];

  constructor(private imageRepository: ImageRepositoryService,
  private modalService : BsModalService,
  private lightbox: Lightbox ) { }

  public getImageThumbUrl(id : number): string{
    return this.imageRepository.getImageThumbUrl(id);
  }

  public getImageUrl(id : number): string{
    return this.imageRepository.getImageUrl(id);
  }

  public pageChanged(event : any) : void {
    this.imageRepository.setNoPage(event.page - 1);
  }

  public openGalerie(image:Image): void{
   /* let album = [];
    album.push({
      src:this.imageRepository.getImageUrl(image.id),
      caption: image.fileName
    });*/
    //this.lightbox.open(album);
    // quelle est la position de l'image cliquée dans l'album
    let position = this.gallerieLiens.findIndex(imglien => imglien.id == image.id);
    // ouvrir l'album à partir de l'image cliquée
    this.lightbox.open(this.gallerieLiens, position, {fadeDuration: 0.2,
                                                      resizeDuration: 0.2,
                                                      showImageNumberLabel: true, 
                                                      wrapAround: true});

  }

  public getImagePopoverText(image:Image): string{
    if(image.tags == null || image.tags.length == 0){
        return"aucun tag";
    } else
      return"tags: " + image.tags.map(t => t.libelle).join(",");
   }


  ngOnInit() {
    this.images = new Subject();
    this.souscription = this.imageRepository.listeImagesObservable()
                                            .subscribe(p => {
                                              // maj liens pour lightbox
                                              this.gallerieLiens = [];
                                              p.content.forEach( img => {
                                                  this.gallerieLiens.push({
                                                    id : img.id,
                                                    src : this.imageRepository.getImageUrl(img.id),
                                                    caption : img.fileName
                                                  });                                           
                                              });
                                              // publier les images pour les ngfor
                                              this.images.next(p.content);
                                              this.totalItems = p.totalElements;
                                              this.currentPage= p.number + 1;
                                             });
    this.imageRepository.refreshList();
  }


  ngOnDestroy(): void {
    this.souscription.unsubscribe();
  }


  public deleteImage(id: number, deleteTemplate : TemplateRef<any>) : void {
    console.log("effacement image No " + id + "demandé");
    this.idToDelete = id;
    this.modalRef = this.modalService.show(deleteTemplate);
 
  }

  public confirmDelete() :void {
    this.modalRef.hide();
    console.log("effacement image No " + this.idToDelete + "confirmé");
    this.imageRepository.deleteImages([this.idToDelete]);

  }

}
