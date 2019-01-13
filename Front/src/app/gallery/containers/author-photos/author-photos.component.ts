import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { FlickSearchService } from '../../services/search/flick-search.service';

import { map, switchMap, tap } from 'rxjs/operators';

import { Photo } from '../../shared/models/photo';

@Component({
  selector: 'app-author-photos',
  templateUrl: './author-photos.component.html',
  styleUrls: ['./author-photos.component.scss']
})
export class AuthorPhotosComponent implements OnInit {
  isLoading = false;
  currentPage: number;
  allPages: number;
  photos: Photo[];

  constructor(private searchService: FlickSearchService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.getAuthorPhotos();
  }

  getAuthorPhotos() {
    this.isLoading = true;
    this.activatedRoute.params
      .pipe(
        switchMap((params) => this.searchService.getAuthorPhotos(params['id'])),
        tap((photosReq) => {
          this.isLoading = false;
          this.currentPage = photosReq.photos.page;
          this.allPages = photosReq.photos.pages;
        }),
        map((photosReq) => photosReq.photos.photo)
      ).subscribe((photos) => {
      this.photos = photos;
    });
  }

  loadMorePhotos() {
    if (this.allPages <= this.currentPage) {
      return;
    }

    this.searchService.getMorePhotos(++this.currentPage)
      .pipe(
        tap((photosReq) => {
          this.isLoading = false;
          this.currentPage = photosReq.photos.page;
          this.allPages = photosReq.photos.pages;
        }),
        map((photosReq) => photosReq.photos.photo)
      ).subscribe((photos) => {
      this.photos = photos;
    });
  }

}
