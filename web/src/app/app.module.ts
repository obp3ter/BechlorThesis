import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from '@angular/material/snack-bar';

import { AppComponent } from './app.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { HabitListComponent } from './habit-list/habit-list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";

import { FlexLayoutModule } from '@angular/flex-layout';


@NgModule({
  imports: [
    ReactiveFormsModule,
    HttpClientModule,
    BrowserModule,
    FormsModule,
    MatSnackBarModule,
    MatButtonModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatToolbarModule,
    FlexLayoutModule,
    RouterModule.forRoot([
      {path: '', component: HabitListComponent},
    ]),
    MatListModule
  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    HabitListComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }


