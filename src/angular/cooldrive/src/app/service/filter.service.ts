import {EventEmitter, Injectable} from "@angular/core";
import {FilterListener} from "../components/maintenance/filterlistener";

@Injectable()
export class FilterService{

  listener: FilterListener;

  constructor(){

  }

  filtered(filter){
    this.listener.onFiltered(filter);
  }
}
