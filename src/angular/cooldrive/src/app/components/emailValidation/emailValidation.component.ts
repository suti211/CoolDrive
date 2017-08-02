import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
/**
 * Created by David Szilagyi on 2017. 08. 02..
 */

@Component({
  selector: 'app-emailValidation',
  templateUrl: './emailValidation.component.html',
})

export class EmailValidation implements OnInit {

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    }
}
