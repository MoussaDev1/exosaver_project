import { CourseService } from '../../services/course-service';
import { Component, OnInit, Input } from '@angular/core';
import { Courses } from '../../models/courses'; // Assuming you have a model for courses
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { BreadcrumbComponent } from '../../shared/breacrumb-component/breacrumb-component';

@Component({
  selector: 'app-courses-component',
  imports: [
    FormsModule,
    RouterLink,
    MatButtonModule,
    MatCardModule,
    MatProgressBarModule,
    BreadcrumbComponent,
  ],
  templateUrl: './courses-component.html',
  styleUrl: './courses-component.scss',
})
export class CoursesComponent implements OnInit {
  // Liste des cours
  @Input() courses: Courses[] = [];

  constructor(private courseService: CourseService, private router: Router) {}

  /*Permet de récupérer tous les cours :
  * Cette méthode est appelée lors de l'initialisation du composant.
  * Elle utilise le service CourseService pour récupérer les cours depuis l'API.
  * Dans le service, on veut un Observable de type Courses[] c'est pour cela qu'on utilise une variable
    de type Courses[].
  */
  ngOnInit(): void {
    this.courseService.getAllCourses().subscribe(
      (result) => {
        this.courses = result;
      },
      (error) => {
        console.error('Error fetching courses:', error);
      }
    );
  }

  /*Permet de récupérer un cours par son id :
  @param id - L'identifiant du cours à récupérer
  * Cette méthode est appelée lorsqu'on clique sur le bouton "Voir le cours".
  * "id" passé en paramètre est récuperable car dans les objets "courses" du tableau on a un id.
  */
  onViewCourse(id: number | undefined): void {
    this.router.navigateByUrl(`course/${id}`);
  }
}
