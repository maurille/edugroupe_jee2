import { Component, OnInit } from '@angular/core';
import{FileUploader} from"ng2-file-upload";
import { ImageRepositoryService } from '../../services/image-repository.service';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.css']
})
export class ImageUploadComponent implements OnInit {

  public uploader: FileUploader;
  public hasBaseDropZoneOver: boolean=false;

  constructor( private imagesRepository : ImageRepositoryService) { 
    this.uploader = new FileUploader({
      autoUpload : true,
      url : this.imagesRepository.getUploadurl()
    });
  }

  ngOnInit() {
  }

  public fileOverDrop(event):void{
    this.hasBaseDropZoneOver =event;

  }

}
