import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AppComponent } from './app.component';
import{FileUploadModule} from "ng2-file-upload";
import { MangaRepositoryService } from './services/manga-repository.service';
import { SearchMangaComponent } from './components/search-manga/search-manga.component';
import { ListeMangaComponent } from './components/liste-manga/liste-manga.component';
import { EditMangaComponent } from './components/edit-manga/edit-manga.component';
import { LoginComponent } from './components/login/login.component';
import { UtilisateurInfoComponent } from './components/utilisateur-info/utilisateur-info.component';
import { AuthInterceptorService } from './services/auth-interceptor.service';
import { AuthManagerService } from './services/auth-manager.service';



@NgModule({
  declarations: [
    AppComponent,
    SearchMangaComponent,
    ListeMangaComponent,
    EditMangaComponent,
    LoginComponent,
    UtilisateurInfoComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    FileUploadModule,
    ProgressbarModule.forRoot(),
    PaginationModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    RouterModule.forRoot([
      {path: 'liste', component: ListeMangaComponent},
      {path: 'edit/:id', component: EditMangaComponent},
      {path: 'login', component: LoginComponent},
      {path: '', redirectTo: '/liste', pathMatch: 'full'}
    ])
  ],
  providers: [MangaRepositoryService,
    AuthManagerService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
