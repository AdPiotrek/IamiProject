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
  @Input() showDetails = true;
  @Input() hasDelete = false;
  @Output() authorClicked = new EventEmitter<string>();
  @Output() deleteClicked = new EventEmitter();
  loaded = false;

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
          this.loaded = true;
        })
      );

  }

  onAuthorClicked() {
    this.authorClicked.emit(this.photo.userId);
  }

  onXClicked() {
    this.deleteClicked.emit(this.photo.blobid);
  }



}
