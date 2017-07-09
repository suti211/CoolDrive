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
export class RegisterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
