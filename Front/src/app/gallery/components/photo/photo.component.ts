import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output, Sanitizer
} from '@angular/core';

import { Photo } from '../../shared/models/photo';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map, tap } from 'rxjs/operators';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-photo',
  templateUrl: './photo.component.html',
  styleUrls: ['./photo.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PhotoComponent implements OnInit {
  @Input() photo: Photo;
  @Input() photoSize = 'n';
  @Output() authorClicked = new EventEmitter<string>();

  photoSrc: Observable<any>;

  constructor(private httpClient: HttpClient,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.photoSrc = this.httpClient.get(`https://localhost:8443/blob/${this.photo.blobid}`, {
      responseType: 'blob'
    })
      .pipe(
        map((resp) => {
          return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(resp));
        }),
        tap((url) => {
          console.log(url);
        })
      );

  }

  onAuthorClicked() {
    this.authorClicked.emit(this.photo);
  }



}
