import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {EmailValidationService} from "../../service/email-validation.service";
import {Status} from "../../model/status.model";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-emailValidation',
  templateUrl: './emailValidation.component.html',
})

export class EmailValidation implements OnInit {
  token: string;
  asd: any;

  showError: boolean;
  errorMsg: string;

  constructor(private route: ActivatedRoute, private router: Router, private emailValidation: EmailValidationService) {}

  ngOnInit() {
    this.asd = this.route.params.subscribe(params => {
      this.token = params['token'];
      }
    )

    let emailValidOperation: Observable<Status>;
    emailValidOperation = this.emailValidation.sendValidation(this.token);
    emailValidOperation.subscribe((status: Status) => {
        localStorage.setItem('emailStatus', JSON.stringify(status));
        this.router.navigate(['login']);
    });


  }
}
