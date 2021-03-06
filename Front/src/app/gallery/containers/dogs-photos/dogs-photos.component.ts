import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FlickSearchService } from '../../services/search/flick-search.service';

import { Observable, pipe } from 'rxjs';
import { finalize, map, tap } from 'rxjs/operators';

import { Photo } from '../../shared/models/photo';
import { DogsFilterValues } from '../../shared/models/dogs-filter-values';
import { latLng, marker, tileLayer, icon, Marker } from 'leaflet';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-dogs-photos',
  templateUrl: './dogs-photos.component.html',
  styleUrls: ['./dogs-photos.component.scss']
})
export class DogsPhotosComponent implements OnInit {
  isLoading = false;
  showModal = false;
  isMapOpen = false;
  currentPage = 1;
  hasMore = true;
  allPages: number;
  photos: any[] = [];
  photoToShow: Photo;
  geoPosition: Position;
  pointers$: Observable<Marker[]>;

  mockedPhoto = {
    src: 'https://getuikit.com/v2/docs/images/placeholder_480x260.svg'
  };

  mockedPhotos = {};

  options = {
    layers: [
      tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' })
    ],
    zoom: 5,
    center: undefined
  };

  constructor(private searchService: FlickSearchService,
              private router: Router,
              private httpClient: HttpClient,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.getNewPhotos();
  }

  getNewPhotos() {
    this.isLoading = true;

    this.httpClient.get('https://localhost:8443/blob/get/0')
      .subscribe((resp: any) => {
        this.isLoading = false;
        console.log(resp);
        this.photos = resp;
      });
  }

  loadMorePhotos() {
    this.isLoading = true;
    this.httpClient.get(`https://localhost:8443/blob/get/${ ++this.currentPage }`)
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      ).subscribe((photos: any) => {
      this.photos = [...this.photos, ...photos];
    });
  }

  seeAuthorPhotos(authorId) {
    if (!this.authService.user) {
      return;
    }
    this.router.navigateByUrl(`author/${ authorId }`);
  }

  showModalWithPhoto(photo: Photo) {
    console.log(photo);
    this.photoToShow = photo;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

}
