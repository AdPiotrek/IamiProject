import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { FlickSearchService } from '../../services/search/flick-search.service';

import { map, switchMap, tap } from 'rxjs/operators';

import { Photo } from '../../shared/models/photo';
import { HttpClient } from '@angular/common/http';

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

  constructor(private httpClient: HttpClient,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.getAuthorPhotos();
  }

  getAuthorPhotos() {
    this.isLoading = true;
    this.activatedRoute.params
      .pipe(
        switchMap((params) => this.httpClient.get(`https://localhost:8443/blob/user/${ params.id }/${ this.currentPage }`)),
        tap((photosReq) => {
          this.isLoading = false;
          console.log(photosReq);
        }),
      ).subscribe((photos: Photo[]) => {
      this.photos = photos;
    });
  }

  loadMorePhotos() {

  }

}
