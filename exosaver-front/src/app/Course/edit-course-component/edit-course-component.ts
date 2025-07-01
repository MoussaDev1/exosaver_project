import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Courses } from '../../models/courses';
import { CourseService } from '../../services/course-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-edit-course-component',
  imports: [FormsModule, RouterLink],
  templateUrl: './edit-course-component.html',
  styleUrl: './edit-course-component.scss',
})
export class EditCourseComponent implements OnInit {
  course: Courses = {
    title: '',
    description: '',
    objectives: '',
    themes: '',
  };

  constructor(
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  /*
   * Permet de récupérer un cours par son id :
   * Cette méthode est appelée lors de l'initialisation du composant.
   * Elle utilise le service CourseService pour récupérer le cours par son id depuis l'API.
   * On attribut d'abord l'id retrouver dans l'URL à une variable "courseId" pour que ce soit dynamique.
   * Ensuite on utilise le service pour appeler la méthode getCourseById en lui passant l'id du cours récupéré.
   * On en a besoin pour pré-remplir le formulaire d'édition du cours.
   */
  ngOnInit(): void {
    const courseId = this.route.snapshot.params['id'];
    this.courseService.getCourseById(courseId).subscribe({
      next: (course) => {
        this.course = course;
      },
    });
  }

  /*
   * Permet de mettre à jour un cours :
   * Cette méthode est appelée lors de la soumission du formulaire.
   * Elle utilise le service CourseService pour mettre à jour le cours par son id depuis l'API.
   * Si l'id du cours est défini, on appelle la méthode updateCourse du service en lui passant l'id et les données du cours.
   * Ensuite, on redirige l'utilisateur vers la page de détail du cours mis à jour.
   */
  onUpdateCourse(form: NgForm) {
    if (this.course.id !== undefined) {
      this.courseService.updateCourse(this.course.id, this.course).subscribe({
        next: (updateCourse) => {
          console.log('Course updated successfully:', updateCourse);
          this.router.navigate(['/course/', this.course.id]);
        },
      });
    }
  }
}
