import { Component } from '@angular/core';
import { exercices } from '../../models/exercices';
import { ExercicesService } from '../../services/exercices-service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-exercice-component',
  imports: [FormsModule],
  templateUrl: './create-exercice-component.html',
  styleUrl: './create-exercice-component.scss',
})
export class CreateExerciceComponent {
  exercice: exercices = {
    title: '',
    description: '',
    solution: 'à remplir',
  };
  exercices: exercices[] = [];

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  onSubmit(ngform: NgForm) {
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    if (idTopicParam !== null && idCourseParam !== null) {
      const idTopic = Number(idTopicParam);
      const idCourse = Number(idCourseParam);
      this.exerciceService
        .createExercicesByTopicId(this.exercice, idCourse, idTopic)
        .subscribe({
          next: (createdExercice) => {
            this.exercices.push(createdExercice);
            this.router.navigate([`/course/${idCourse}/topic/${idTopic}`]);
          },
          error: (error) => {
            console.error('Error creating exercice:', error);
          },
        });
    }
  }

  onReturnTopicView() {}
}
