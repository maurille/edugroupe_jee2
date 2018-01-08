import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeTacheComponent } from './liste-tache.component';

describe('ListeTacheComponent', () => {
  let component: ListeTacheComponent;
  let fixture: ComponentFixture<ListeTacheComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListeTacheComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeTacheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
