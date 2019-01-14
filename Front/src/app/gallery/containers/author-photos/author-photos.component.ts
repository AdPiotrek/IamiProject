import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { FlickSearchService } from '../../services/search/flick-search.service';

import { map, switchMap, tap } from 'rxjs/operators';

import { Photo } from '../../shared/models/photo';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-author-photos',
  templateUrl: './author-photos.component.html',
  styleUrls: ['./author-photos.component.scss']
})
export class AuthorPhotosComponent implements OnInit {
  isLoading = false;
  currentPage = 0;
  allPages: number;
  photos: Photo[];
  userId;
  loggedUserId;
  selectedUser: Observable<any>

  constructor(private httpClient: HttpClient,
              private activatedRoute: ActivatedRoute,
              private authService: AuthService,
              private toastService: ToastrService) {
  }

  ngOnInit() {
    this.getAuthorPhotos();
  }

  getAuthorPhotos() {
    this.isLoading = true;
    this.activatedRoute.params
      .pipe(
        switchMap((params) => {
          this.selectedUser = this.httpClient.get(`https://localhost:8443/user/get/${ params.id }`);
          this.userId = params.id;
          this.loggedUserId = this.authService.user.userid;
          return this.httpClient.get(`https://localhost:8443/blob/user/${ params.id }/${ this.currentPage }`);
        }),
        tap((photosReq) => {
          this.isLoading = false;

          console.log(photosReq);
        }),
      ).subscribe((photos: Photo[]) => {
      this.photos = photos;
    });
  }

  loadMorePhotos() {
    this.activatedRoute.params
      .pipe(
        switchMap((params) => {
          this.userId = params.id;
          this.loggedUserId = this.authService.user.userid;
          return this.httpClient.get(`https://localhost:8443/blob/user/${ params.id }/${ ++this.currentPage }`);
        }),
        tap((photosReq) => {
          this.isLoading = false;
          console.log(photosReq);
        }),
      ).subscribe((photos: Photo[]) => {
      this.photos = [...this.photos, ...photos];
    });
  }

  deleteBlob(id) {
    console.log('[DELETE BLOB]', id);
    this.httpClient.delete(`https://localhost:8443/blob/delete/${ id }`)
      .subscribe(() => {
          this.toastService.success('Zdjęcie usunięte');
        },
        () => {
          this.toastService.error('Wystąpił błąd spróbuj ponownie później');
        });
  }

}
