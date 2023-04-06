import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  set(key:string,value:any){
    localStorage.setItem(key,JSON.stringify(value));
  }
  get(key:string){
    let result=localStorage.getItem(key)||"";
    return JSON.parse(result);
  }
  remove(key:string){
    localStorage.removeItem(key);
  }
  constructor() { }
}
