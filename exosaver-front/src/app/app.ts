import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { SidnavComponent } from './shared/sidnav-component/sidnav-component';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SidnavComponent, MatSidenavModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected title = 'exosaver-front';
}
