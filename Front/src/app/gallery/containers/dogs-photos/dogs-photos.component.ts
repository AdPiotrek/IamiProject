import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FlickSearchService } from '../../services/search/flick-search.service';

import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

import { Photo } from '../../shared/models/photo';
import { DogsFilterValues } from '../../shared/models/dogs-filter-values';
import { latLng, marker, tileLayer, icon, Marker } from 'leaflet';

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
  allPages: number;
  photos: Photo[];
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
              private router: Router) {
  }

  ngOnInit() {
  }

  getNewPhotos(filterValues?: DogsFilterValues) {
    this.isLoading = true;
    let url = '';

    if (filterValues) {
      url = this.searchService.prepareUrlFromSingleValueFilter(filterValues.license, 'license');
      url = url + this.searchService.prepareUrlFromSingleValueFilter(filterValues.search, 'text');
      url = url + this.searchService.prepareUrlFromColorFilter(filterValues.colors);
    }

    this.searchService.getPhotos(url)
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

  loadMorePhotos() {
    if (this.allPages <= this.currentPage) {
      return;
    }

    this.isLoading = true;
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

  seeAuthorPhotos(authorId) {
    this.router.navigateByUrl(`author/${ authorId }`);
  }

  showModalWithPhoto(photo: Photo) {
    this.photoToShow = photo;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  openMaps() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.geoPosition = position;
      this.isMapOpen = true;
      this.pointers$ = this.searchService.getDogsLocalization(position)
        .pipe(
          map((dogs) => {
            return dogs.map((dog) => {
              return marker(latLng(+dog.latitude, +dog.longitude),
                {
                  title: dog.title, icon: icon({ iconUrl: 'marker-icon.png' })
                });
            });
          })
        );

      this.options.center = latLng(position.coords.latitude, position.coords.longitude);

    }, err => {
      if (err.code === 1) {
        alert('You denied permissions, try to reset consents in your browser');
      }
    });
  }

  closeMaps() {
    this.isMapOpen = false;
  }
}
