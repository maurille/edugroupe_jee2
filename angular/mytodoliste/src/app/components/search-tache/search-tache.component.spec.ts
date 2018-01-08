import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchTacheComponent } from './search-tache.component';

describe('SearchTacheComponent', () => {
  let component: SearchTacheComponent;
  let fixture: ComponentFixture<SearchTacheComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchTacheComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchTacheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
