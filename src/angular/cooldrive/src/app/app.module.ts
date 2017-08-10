import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { HttpModule} from "@angular/http";
import { AppComponent } from "./app.component";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { LoginService } from "./service/login.service";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RegisterService } from "./service/register.service";
import { LoginGuard } from "./guard/login.guard";
import { FilesComponent } from "./components/files/files.component";
import { FileService } from "./service/files.service";
import { TokenService } from "./service/token.service";
import { LogoutService } from "./service/logout.service";
import { ExtensionComponent } from "./components/storage_extension/extension.component";
import { CheckoutComponent } from "./components/checkout/checkout.component";
import { TransactionService } from "./service/transaction.service";
import { CheckoutService } from "./service/checkout.service";
import { PaymentComponent } from "./components/payment/payment.component";
import { TransactionStatus } from "./components/transaction_status/transaction.status.component";
import { EmailValidation } from "./components/emailValidation/emailValidation.component";
import { EmailValidationService } from "./service/email-validation.service";
import { TransactionList } from "./components/maintenance/transactionlist.component";
import { HttpClient } from "./service/http.client";
import { UnauthorizedComponent } from "./components/unauthorized/unauthorized.component";
import { UsersComponent } from './components/users/users.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ShareService } from "./service/share.service";
import { SharedWithMeComponent } from './components/shared-with-me/shared-with-me.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    FilesComponent,
    ExtensionComponent,
    CheckoutComponent,
    PaymentComponent,
    TransactionStatus,
    EmailValidation,
    TransactionList,
    UnauthorizedComponent,
    UsersComponent
    LandingPageComponent,
    SharedWithMeComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
      { path: "verify/:token", component: EmailValidation },
      { path: "login", component: LoginComponent },
      { path: "register", component: RegisterComponent },
      { path: "dashboard", component: DashboardComponent, children:
       [
          { path: "files", component: FilesComponent},
          { path: "storage",  component: ExtensionComponent},
          { path: "checkout",  component: CheckoutComponent},
          { path: "payment/:id",  component: PaymentComponent},
          { path: "transaction",  component: TransactionStatus},
          { path: "users", component: UsersComponent },
          { path: "transactions", component: TransactionList },
          { path: "unauthorized", component: UnauthorizedComponent },
          { path: 'shared', canActivate: [LoginGuard], component: SharedWithMeComponent},
        ]
      },
      {path: "**", component: LandingPageComponent}
    ])
  ],

  providers: [LoginService, RegisterService, LoginGuard, FileService, TokenService, LogoutService, TransactionService, CheckoutService, EmailValidationService, HttpClient, ShareService],
  bootstrap: [AppComponent]
})
export class AppModule { }
