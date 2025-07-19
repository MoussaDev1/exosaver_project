import { Component, OnInit } from '@angular/core';
import { Courses } from '../../models/courses';
import {
  ActivatedRoute,
  Router,
  RouterLink,
  RouterOutlet,
} from '@angular/router';
import { CourseService } from '../../services/course-service';
import { FormsModule, NgForm } from '@angular/forms';
import { TopicsOfOneCourseComponent } from '../../Topic/topics-of-one-course-component/topics-of-one-course-component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { TopicService } from '../../services/topic-service';
import { HeaderModule } from '../../shared/header-module/header-module';

@Component({
  selector: 'app-one-course-component',
  imports: [
    FormsModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    TopicsOfOneCourseComponent,
    HeaderModule,
  ],
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
  topicCount = 0;

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService,
    private router: Router,
    private topicService: TopicService
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
    const courseId = this.route.snapshot.params['idCourse'];
    if (courseId) {
      this.topicService.getAllTopicsByCourseId(courseId).subscribe((topics) => {
        this.topicCount = topics.length;
      });
    }
  }

  private getCourseById(): void {
    const courseId = this.route.snapshot.params['idCourse'];
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
