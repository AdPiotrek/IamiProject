import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { DogsPhotosComponent } from './gallery/containers/dogs-photos/dogs-photos.component';
import { AuthorPhotosComponent } from './gallery/containers/author-photos/author-photos.component';
import { NavigationComponent } from './navigation/navigation.component';
import { PhotoUploadComponent } from './gallery/containers/photo-upload/photo-upload.component';
import { SunInfoComponent } from './gallery/containers/sun-info/sun-info.component';

const routes: Routes = [
  { path: '', redirectTo: '/dogs', pathMatch: 'full' },
  { path: '', component: NavigationComponent, children: [
      { path: 'dogs', component: DogsPhotosComponent },
      { path: 'author/:id', component: AuthorPhotosComponent },
      { path: 'photo-upload', component: PhotoUploadComponent},
      { path: 'sun-info', component: SunInfoComponent}
    ]}
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
