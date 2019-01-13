import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { PhotoComponent } from './gallery/components/photo/photo.component';
import { PhotosComponent } from './gallery/components/photos/photos.component';
import { LoaderComponent } from './shared/loader/loader.component';
import { DogsPhotosComponent } from './gallery/containers/dogs-photos/dogs-photos.component';
import { AuthorPhotosComponent } from './gallery/containers/author-photos/author-photos.component';
import { AppRoutingModule } from './app-routing.module';
import { ModalComponent } from './shared/modal/modal.component';

import { SwingErrorHandlerService } from './core/services/error-handler/swing-error-handler.service';
import { FlickApiInterceptorService } from './core/services/interceptor/flick-api-interceptor.service';

import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { ToastrModule } from 'ngx-toastr';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { NavigationComponent } from './navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import {
  MatToolbarModule,
  MatButtonModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatInputModule,
  MatDatepickerModule, MatNativeDateModule
} from '@angular/material';
import { PhotoUploadComponent } from './gallery/containers/photo-upload/photo-upload.component';
import { CommonModule } from '@angular/common';
import { InputFileModule } from 'ngx-input-file';
import { SunInfoComponent } from './gallery/containers/sun-info/sun-info.component';
import { LineChartModule, NgxChartsModule } from '@swimlane/ngx-charts';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PhotoComponent,
    PhotosComponent,
    LoaderComponent,
    DogsPhotosComponent,
    AuthorPhotosComponent,
    ModalComponent,
    NavigationComponent,
    PhotoUploadComponent,
    SunInfoComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    InfiniteScrollModule,
    AppRoutingModule,
    ToastrModule.forRoot({
      preventDuplicates: true
    }),
    LeafletModule.forRoot(),
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    InputFileModule.forRoot({}),
    MatDatepickerModule,
    MatNativeDateModule,
    NgxChartsModule,
  ],
  providers: [
    {
      provide: ErrorHandler,
      useClass: SwingErrorHandlerService
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: FlickApiInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
