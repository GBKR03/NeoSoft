import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserRegistrationService } from '../user-registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: User= new User("","","","","","","","");
  message:any;

  constructor(private service:UserRegistrationService) { }

  ngOnInit() {
  }

  public userRegistration(){
    let resp = this.service.doUserRegistration(this.user);
    resp.subscribe((data)=>this.message=data);
  }

}
