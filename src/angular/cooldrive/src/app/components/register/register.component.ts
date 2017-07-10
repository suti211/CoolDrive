import { Component, OnInit } from '@angular/core';
import { slideInOutAnimation } from '../../_animations/slide/slide.animation';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  animations: [slideInOutAnimation],
  // attach the fade in animation to the host (root) element of this component
  host: { '[@slideInOutAnimation]': '' }
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  repeat = '';
  alertMsg = '';


  addAlertMsg(msg) {
    this.alertMsg += msg + '<br>';
  }

  setAlert(type) {
    const alertElement = document.getElementById('alert')

    if (type === 'success') {
      alertElement.className = 'alert alert-success';
      alertElement.innerHTML = this.alertMsg;
    }

    if (type === 'alert') {
      alertElement.className = 'alert alert-danger';
      alertElement.innerHTML = this.alertMsg;
    }
    this.alertMsg = '';
  }

  finalize() {
    let success = true;
    if (!this.checkUserNameLength()) {
      const pwdField = document.getElementById('username');
      pwdField.style.backgroundColor = '#f2dede';
      success = false;
    }
    if (!this.checkValidEmail()) {
      const repeatedPwdField = document.getElementById('email');
      repeatedPwdField.style.backgroundColor = '#f2dede';
      success = false;
    }
    if (!this.checkValidPassword()) {
      const pwdField = document.getElementById('pwd');
      pwdField.style.backgroundColor = '#f2dede';
      success = false;
    }
    if (!this.checkValidRepeatedPassword()) {
      const repeatedPwdField = document.getElementById('repeatedPwdField');
      repeatedPwdField.style.backgroundColor = '#f2dede';
      success = false;
    }
    if (success) {
      this.addAlertMsg('Registration was successfull!');
      this.addAlertMsg('You will be redirected in a moment...');
      this.setAlert('success');
    }else {
      this.setAlert('alert');
    }

  }

  checkUserNameLength() {
    if (this.username.length === 0) {
      this.addAlertMsg('Username field is empty!');
      return false;
    }
    return true;
  }

  checkValidEmail() {
    if (this.email.length === 0) {
      this.addAlertMsg('Email field is empty!');
      return false;
    }
    return true;
  }

  checkValidPassword() {
    const pwdField = document.getElementById('pwd');
    pwdField.style.backgroundColor = '#ffffff';

    if (this.password.length === 0) {
      this.addAlertMsg('Password field is empty!');
      return false;
    }
    if (this.password.length >= 6) {
      return true;
    }else {
      this.addAlertMsg('Password is too short! (atleast 6 char)');
      return false;
    }
  }

  checkValidRepeatedPassword() {
    if (this.repeat.length === 0) {
      this.addAlertMsg('Repeated password field is empty!');
      return false;
    }
    if (this.password !== this.repeat) {
      this.addAlertMsg('Repeated password does not match!');
      return false;
    }
    return true;
  }

  resetFieldBackgroundColor(elementID) {
    const field = document.getElementById(elementID);
    field.style.backgroundColor = '#ffffff';
  }

}
