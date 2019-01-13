import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth/auth.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private toastService: ToastrService,
              private router: Router,
              private httpClient: HttpClient,
              private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit() {
  }

  loginUser() {
    this.httpClient.post('https://localhost:8443/login', this.loginForm.value)
      .pipe(
        switchMap((resp: any) => {
          this.authService.token = resp.Authorization;
          return this.httpClient.get('https://localhost:8443/user/get');
        })
      )
      .subscribe((resp: any) => {
        this.authService.user = resp;
      }
      );
  }

}
