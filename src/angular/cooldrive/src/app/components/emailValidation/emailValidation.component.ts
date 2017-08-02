import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
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

  constructor(private route: ActivatedRoute, private emailValidation: EmailValidationService) {}

  ngOnInit() {
    this.asd = this.route.params.subscribe(params => {
      this.token = params['token'];
      }
    )

    let emailValidOperation: Observable<Status>;
    emailValidOperation = this.emailValidation.sendValidation(this.token);
    emailValidOperation.subscribe((status: Status) => {
      console.log(status);
      console.log(status.message);
    });


  }
}
