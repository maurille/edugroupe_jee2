import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { TagRepositoryService } from '../../services/tag-repository.service';
import { Tag } from '../../models/tag';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { ImageRepositoryService } from '../../services/image-repository.service';


@Component({
  selector: 'app-tag-selector',
  templateUrl: './tag-selector.component.html',
  styleUrls: ['./tag-selector.component.css']
})
export class TagSelectorComponent implements OnInit {

  // mode 0 -> liste/filtrage
  // mode 1 -> edition/image
  @Input()
  public tagMode: number = 0;

  @Output()
  public tagAddChange: EventEmitter<Tag> = new EventEmitter<Tag>();

  @Output()
  public tagRemoveChange: EventEmitter<Tag> = new EventEmitter<Tag>();

  constructor(private tagrepository : TagRepositoryService,
              private imageRepository: ImageRepositoryService) { }

  public tagSubject : Subject<Tag[]>;
  public tagSubscription :  Subscription;

  public tagSelected : Observable<Tag[]>;

  ngOnInit() {
    console.log("tab mode " + this.tagMode)
    this.tagSubject = new Subject<Tag[]>();
    this.tagSubscription = this.tagrepository.listetagAsObservable()
                                            .subscribe( p => {
                                              this.tagSubject.next(p.content);
                                            });
    this.tagrepository.refreshListe();
    this.tagSelected = this.imageRepository.selectedtagsAsObservable();
  }

  public addToSelectedTag(tag: Tag) : void {
   //this.imageRepository.addSelectedTag(tag);
    this.tagAddChange.emit(tag);
  }

  public removeFromSelectedTag(tag: Tag) : void {
    //this.imageRepository.removeSelectedTag(tag); cette ligne passe dans list.component
    // cela veut dire que au lieu que ce soit tag qui le face c'est plutot le parent qui le fassse
    this.tagRemoveChange.emit(tag);
  }

}
