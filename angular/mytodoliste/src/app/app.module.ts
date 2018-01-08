import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import{HttpClientModule} from '@angular/common/http';
import{FormsModule} from '@angular/forms';
import { RouterModule } from "@angular/router";


import { AppComponent } from './app.component';
import { ListeTacheComponent } from './components/liste-tache/liste-tache.component';
import { EditTacheComponent } from './components/edit-tache/edit-tache.component';
import { SearchTacheComponent } from './components/search-tache/search-tache.component';
import { TacheRepositoryService } from './services/tache-repository.service';


@NgModule({
  declarations: [
    AppComponent,
    ListeTacheComponent,
    EditTacheComponent,
    SearchTacheComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      {path:'liste', component: ListeTacheComponent},
      {path: 'edit/:id', component: EditTacheComponent},
      {path: '', redirectTo: '/liste', pathMatch: 'full'}
    ])
  ],
  providers: [TacheRepositoryService],
  bootstrap: [AppComponent]
})
export class AppModule { }
