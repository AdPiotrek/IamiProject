import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { FormBuilder } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

  user;
  password = '';
  passwordRepeated = '';
  email = '';
  emailRepeated = '';

  constructor(private authService: AuthService,
              private httpClient: HttpClient,
              private toastService: ToastrService) {

  }

  ngOnInit() {
    this.user = { ...this.authService.user };
  }

  saveEmail() {
    const params = new HttpParams().append('email', this.user.email);
    this.httpClient.put(`https://localhost:8443/user/edit?name=&password=&surname=`, {}, { params, responseType: 'text' })
      .subscribe(() => {
        this.toastService.success('Dane zmienione');

        this.authService.user = {
          ...this.authService.user,
          email: this.user.email
        };
      });
  }

  saveData() {
    const params = new HttpParams()
      .append('name', this.user.name)
      .append('surname', this.user.surname);
    this.httpClient.put(`https://localhost:8443/user/edit?password=&email=`, {}, { params, responseType: 'text' })
      .subscribe(() => {
        this.toastService.success('Dane zmienione');

        this.authService.user = {
          ...this.authService.user,
          name: this.user.name,
          surname: this.user.surname
        };
      });
  }

  savePassword() {
    const params = new HttpParams()
      .append('password', this.password);
    this.httpClient.patch(`https://localhost:8443/user/edit?&password=&email=`, {}, { params, responseType: 'text' })
      .subscribe(() => {
        this.toastService.success('Dane zmienione');
      });
  }
}
