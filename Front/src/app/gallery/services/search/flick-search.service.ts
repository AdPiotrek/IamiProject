import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map, mergeMap, tap } from 'rxjs/operators';

import { PhotoRequest } from '../../shared/models/photo-request';
import { Photo } from '../../shared/models/photo';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FlickSearchService {

  private loadedPhotos: Photo[];
  private previousUsedUrl: string;

  constructor(private http: HttpClient) {
  }

  prepareUrlFromColorFilter(filterFormValue: { [key: string]: boolean }) {
    return Object.entries(filterFormValue)
      .filter(([key, value]) => value)
      .reduce((prev, [key, value], index) => {
        if (index > 0) {
          return `${prev}%2C${key}`;
        }
        return `&color_codes=${key}`;
      }, '');
  }

  prepareUrlFromSingleValueFilter(filterFormValue: string, property: string) {
    return filterFormValue !== '' ? `&${property}=${filterFormValue}` : '';
  }


  getPhotos(appendedParameters: string): Observable<PhotoRequest> {
    this.previousUsedUrl = `https://api.flickr.com/services/rest/?method=flickr.photos.search` +
      `&api_key=${environment.apiKey}` +
      `&format=json&nojsoncallback=1` +
      `&extras=owner_name,description,date_taken` +
      `&perpage=100` +
      `&tags=dog,dogs` +
      appendedParameters;
    return this.http.get<PhotoRequest>(this.previousUsedUrl)
      .pipe(
        tap(photoReq => this.loadedPhotos = [...photoReq.photos.photo]),
      );
  }

  getMorePhotos(page: number): Observable<PhotoRequest> {
    return this.http.get<PhotoRequest>(`${this.previousUsedUrl}&page=${page}`)
      .pipe(
        tap(photoReq => this.loadedPhotos = [...this.loadedPhotos, ...photoReq.photos.photo]),
        mergeMap((photoReq: PhotoRequest) => {
          return of({
            ...photoReq,
            photos: {
              ...photoReq.photos,
              photo: this.loadedPhotos
            }
          });
        }),
      );

  }

  getAuthorPhotos(authorId: string): Observable<PhotoRequest> {
    this.previousUsedUrl = `https://api.flickr.com/services/rest/?method=flickr.photos.search`
      + `&api_key=${environment.apiKey}` +
      `&user_id=${authorId}` +
      `&extras=owner_name,description,date_taken` +
      `&format=json&nojsoncallback=1&page=1`;
    return this.http.get<PhotoRequest>(this.previousUsedUrl)
      .pipe(
        tap(photoReq => this.loadedPhotos = [...photoReq.photos.photo]),
      );
  }

  getDogsLocalization(position: Position): Observable<Photo[]> {
    const url = `https://api.flickr.com/services/rest/?method=flickr.photos.search` +
      `&api_key=${environment.apiKey}` +
      `&format=json&nojsoncallback=1` +
      `&extras=geo` +
      `&tags=dog,dogs` +
      `&lat=${position.coords.latitude}` +
      `&lon=${position.coords.longitude}`;


    return this.http.get<PhotoRequest>(url)
      .pipe(
        map(photoReq => photoReq.photos.photo),
      );
  }
}
