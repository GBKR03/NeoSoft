import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor(private http:HttpClient) { }

  public doUserRegistration(user:any){
    return this.http.post("http://localhost:8080/user", user);
  }

  public findByUserDetails(firstname:string,lastname:string,pincode:string){
    return this.http.get("http://localhost:8080/user", {
    params:{"firstName": firstname, "lastname:":lastname, "pincode":pincode}, observe: 'response'});
  }

  public deleteUser(softdelete:number,userid:string){
    return this.http.delete("http://localhost:8080/user", {
    params:{"softdelete": softdelete, "userid:":userid}, observe: 'response'});
  }
}