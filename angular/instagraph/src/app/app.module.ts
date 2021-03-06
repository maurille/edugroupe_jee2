import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { ImageListComponent } from './components/image-list/image-list.component';
import { TagSelectorComponent } from './components/tag-selector/tag-selector.component';
import { ImageRepositoryService } from './services/image-repository.service';
import{FileUploadModule} from "ng2-file-upload";
import { ImageUploadComponent } from './components/image-upload/image-upload.component';
import { LightboxModule } from 'angular2-lightbox';
import { NgStringPipesModule } from 'angular-pipes';
import{NgMathPipesModule} from 'angular-pipes';
import{PopoverModule} from'ngx-bootstrap/popover'
import { TagRepositoryService } from './services/tag-repository.service';
import{AuthInterceptorService} from "./services/auth-interceptor.service";
import { LoginComponent } from './components/login/login.component'
import { AuthManagerService } from 'app/services/auth-manager.service';
import { UtilisateurInfoComponent } from './components/utilisateur-info/utilisateur-info.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import{AlertModule} from "ngx-bootstrap/alert";
import { AlertDisplayComponent } from './components/alert-display/alert-display.component'
import { AlertManagerService } from 'app/services/alert-manager.service';




@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    ImageListComponent,
    TagSelectorComponent,
    ImageUploadComponent,
    LoginComponent,
    UtilisateurInfoComponent,
    RegisterUserComponent,
    AlertDisplayComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    FileUploadModule,
    LightboxModule,
    NgStringPipesModule,
    NgMathPipesModule,
    PopoverModule.forRoot(),
    ProgressbarModule.forRoot(),
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
    AlertModule.forRoot(),
    RouterModule.forRoot([
      {path: 'liste', component: ImageListComponent},
      {path: 'upload', component: ImageUploadComponent},
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterUserComponent},
      {path: '', redirectTo: '/liste', pathMatch: 'full'}
    ])
  ],
  providers: [
    ImageRepositoryService,
    TagRepositoryService,
    AlertManagerService,
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
