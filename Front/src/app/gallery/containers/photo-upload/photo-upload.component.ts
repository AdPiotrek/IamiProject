import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { HttpClient, HttpParams } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-photo-upload',
  templateUrl: './photo-upload.component.html',
  styleUrls: ['./photo-upload.component.scss'],
  providers: [DatePipe]
})
export class PhotoUploadComponent implements OnInit {
  file: any[];
  localization: string;
  description: string;
  time: string;
  date: Date;
  cords;

  constructor(private toastService: ToastrService,
              private httpClient: HttpClient,
              private datePipe: DatePipe) {
  }

  ngOnInit() {
  }

  fileRejected() {
    this.toastService.error('Plik który probóbujesz wstawić nie jest zdjęciem');
  }

  fileAccepted() {
    setTimeout(() => {
      console.log(this.file[0].file.lastModifiedDate);
      const fileDate: Date = this.file[0].file.lastModifiedDate;
      this.time = this.datePipe.transform(fileDate, 'HH:MM');
      this.date = fileDate;
    }, 0);

  }

  localizeMe() {
    navigator.geolocation.getCurrentPosition(({ coords }) => {
      this.cords = coords;
      const params = new HttpParams()
        .append('key', 'a96d325087b1489194862db63a13c106');
      this.httpClient.get(`https://api.opencagedata.com/geocode/v1/json?q=${ coords.latitude }%2C${ coords.longitude }`,
        { params })
        .subscribe(
          (resp: any) => {
            this.localization = resp.results[0].components.city;
          },
          (err) => {
            this.toastService.error('Nie udało się ustalić twojej lokalizacji !');
          }
        );
    });
  }

  uploadPhoto() {
    const fileToUpload = this.file[0].file;

    const wrappedFile: FormData = new FormData();
    wrappedFile.append('file', fileToUpload, fileToUpload.name);
    const params = new HttpParams()
      .append('fileName', fileToUpload.name)
      .append('description', this.description)
      .append('localizationUF', this.localization)
      .append('latitude', this.cords.latitude)
      .append('longtitude', this.cords.longitude);

    this.httpClient.post(`https://localhost:8443/blob`, wrappedFile,)
      .subscribe((resp) => {
          console.log(resp)
        },
        (err) => {
          if (err.status === 409) {
            this.growlService.addError('Zduplikowana nazwa pliku');
          }
        });
  }
}

