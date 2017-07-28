import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginService } from './service/login.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterService } from './service/register.service';
import { LoginGuard } from './guard/login.guard';
import { FilesComponent } from './components/files/files.component';
import { FileService } from './service/files.service';
import { TokenService } from './service/token.service';
import { LogoutService } from './service/logout.service';
import { ExtensionComponent } from './components/storage_extension/extension.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { TransactionService } from './service/transaction.service';
import { CheckoutService } from './service/checkout.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    FilesComponent,
    ExtensionComponent,
    CheckoutComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'dashboard', component: DashboardComponent, canActivate: [LoginGuard], children:
       [
          { path: 'files', component: FilesComponent},
          { path: 'storage', canActivate: [LoginGuard], component: ExtensionComponent},
          { path: 'checkout/:id', canActivate: [LoginGuard], component: CheckoutComponent}
        ]
      },
      {path: "**", component: LoginComponent}

      
    ])
  ],
  
  providers: [LoginService, RegisterService, LoginGuard, FileService, TokenService, LogoutService, TransactionService, CheckoutService],
  bootstrap: [AppComponent]
})
export class AppModule { }
