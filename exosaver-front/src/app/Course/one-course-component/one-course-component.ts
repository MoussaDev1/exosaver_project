import { Component, Input, NgModule, OnInit } from '@angular/core';
import { Courses } from '../../models/courses';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CourseService } from '../../services/course-service';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-one-course-component',
  imports: [FormsModule, RouterLink],
  templateUrl: './one-course-component.html',
  styleUrl: './one-course-component.scss',
})
export class OneCourseComponent implements OnInit {
  course: Courses = {
    title: '',
    description: '',
    objectives: '',
    themes: '',
  };

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService,
    private router: Router
  ) {}

  /*   * Permet de récupérer un cours par son id :
   * Cette méthode est appelée lors de l'initialisation du composant.
   * Elle utilise le service CourseService pour récupérer le cours par son id depuis l'API.
   * On attribut d'abord l'id retrouver dans l'URL à une variable "courseId" pour que ce soit dynamique.
   * Ensuite on utilise le service pour appeler la méthode getCourseById en lui passant l'id du cours récupéré.
   *
   */
  ngOnInit(): void {
    this.getCourseById();
  }

  private getCourseById(): void {
    const courseId = this.route.snapshot.params['id'];
    this.courseService.getCourseById(courseId).subscribe({
      next: (course) => {
        this.course = course;
      },
    });
  }
  /*
   * Permet de supprimer un cours par son id :
   * Cette méthode est appelée lorsqu'on clique sur le bouton "Supprimer le cours".
   * Elle utilise le service CourseService pour supprimer le cours par son id depuis l'API.
   * Si l'id est défini, on appelle la méthode deleteCourse du service en lui passant l'id du cours.
   * Ensuite, on redirige l'utilisateur vers la page d'accueil.
   */
  onDeleteCourse(id: number | undefined): void {
    if (id !== undefined) {
      this.courseService.deleteCourse(id).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
      });
    }
  }

  /*
   * Permet de mettre à jour un cours par son id :
   * Cette méthode est appelée lorsqu'on clique sur le bouton "Modifier le cours".
   * Elle redirige l'utilisateur vers la page d'édition du cours en utilisant l'id du cours.
   */
  onViewUpdateCourse(id: number | undefined): void {
    this.router.navigateByUrl(`course/edit/${id}`);
  }
}
