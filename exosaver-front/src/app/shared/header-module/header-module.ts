import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-header-module',
  imports: [MatCardModule],
  templateUrl: './header-module.html',
  styleUrl: './header-module.scss',
})
export class HeaderModule {
  @Input() title?: string;
  @Input() subtitle?: string;
  @Input() count?: number;
  @Input() moduleName?: string;

  constructor() {
    // Initialization logic can go here if needed
  }
}
