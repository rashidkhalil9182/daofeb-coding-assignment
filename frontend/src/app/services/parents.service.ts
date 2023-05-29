import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ParentsService {

  apiURL: string = environment.apiUrl;

  constructor(
    private http: HttpClient
  ) {}

  getUrl(uri: string): string {
    console.log('this.apiurl = ' + this.apiURL);
    return this.apiURL + uri;
  }

  getAllParents(page:number,sortOrder:string) {
    return this.http.get(this.getUrl('parents?page=' + page+"&sortOrder=" + sortOrder))
    // this.http.get(this.getUrl('parents')).subscribe((res:any) => {
    //   if(callback) callback(res);
    // })
  }

  getById(id:number, callback?:Function) {
    this.http.get(this.getUrl('parent/' + id)).subscribe((res:any) => {
      if(callback) callback(res);
    })
  }
}
