import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistrationComponent } from './registration/registration.component';
import { SearchUpdateComponent } from './search-update/search-update.component';

const routes: Routes = [
  {path:"", redirectTo:"register", pathMatch:"full"},
  {path:"register", component:RegistrationComponent},
  {path:"search", component:SearchUpdateComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
