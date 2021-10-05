import { Component, OnInit } from '@angular/core';
import { UserRegistrationService } from '../user-registration.service';

@Component({
  selector: 'app-search-update',
  templateUrl: './search-update.component.html',
  styleUrls: ['./search-update.component.css']
})
export class SearchUpdateComponent implements OnInit {

  users:any;
  pincode:string;
  firstname:string;
  surname:string;
  softdelete:number;
  userid:string;
  message:any;


  constructor(private service: UserRegistrationService) { }

  ngOnInit(){
    resp.subscribe((data)=>this.users=data);
  }

    public findBBUUerDetails(){
    let resp = this.service.findByUserDetails(this.firstname, this.surname, this.pincode);
    resp.subscribe((data)=>this.users=data);
  }

  public deleteUser{
     let resp = this.service.deleteUser(this.softdelete, this.userid);
    resp.subscribe((data)=>this.message=data);
  }

  


}
