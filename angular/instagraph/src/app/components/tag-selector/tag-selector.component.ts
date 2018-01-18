import { Component, OnInit } from '@angular/core';
import { TagRepositoryService } from 'app/services/tag-repository.service';
import { Tag } from 'app/models/tag';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { ImageRepositoryService } from 'app/services/image-repository.service';

@Component({
  selector: 'app-tag-selector',
  templateUrl: './tag-selector.component.html',
  styleUrls: ['./tag-selector.component.css']
})
export class TagSelectorComponent implements OnInit {

  public tagSouscription : Subscription;
  public tagSubject : Subject<Tag[]>;
  public tagSelected : Observable<Tag[]>;
  constructor(private tagrepository : TagRepositoryService, private imageRepository: ImageRepositoryService) { 
    
  }

  ngOnInit() {
    this.tagSubject = new Subject<Tag[]>();
    this.tagSouscription = this.tagrepository.listeImagesObservable()
                                            .subscribe(p => {this.tagSubject.next(p.content);
                                              
                                             });
    this.tagrepository.refreshList();
    this.tagSelected = this.imageRepository.selectedtagsAsObservable();
  }

public addToSelectedTag(tag : Tag) : void{
    this.imageRepository.addSelectedTag(tag);
  }

  public removeFromSelectedTag(tag : Tag) : void{
    this.imageRepository.removeSelectedTags(tag);
  }

}
