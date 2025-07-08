import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ExercicesService } from '../../services/exercices-service';
import { exercices } from './../../models/exercices';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-edit-exercice-component',
  imports: [FormsModule],
  templateUrl: './edit-exercice-component.html',
  styleUrl: './edit-exercice-component.scss',
})
export class EditExerciceComponent implements OnInit {
  exercice: exercices = {
    title: '',
    description: '',
  };

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idExerciceParam = this.route.snapshot.paramMap.get('idExercice');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');

    if (idCourseParam && idExerciceParam && idTopicParam) {
      const idCourse = Number(idCourseParam);
      const idExercice = Number(idExerciceParam);
      const idTopic = Number(idTopicParam);
      this.exerciceService
        .getExerciceById(idCourse, idTopic, idExercice)
        .subscribe({
          next: (exercice) => {
            this.exercice = exercice;
          },
          error: (error) => {
            console.error('Error fetching exercice:', error);
          },
        });
    } else {
      console.error(
        'Course ID, Exercice ID or Topic ID is missing in the route parameters.'
      );
      return;
    }
  }

  onUpdateExercice(form: NgForm) {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idExerciceParam = this.route.snapshot.paramMap.get('idExercice');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');

    if (idCourseParam && idExerciceParam && idTopicParam) {
      const idCourse = Number(idCourseParam);
      const idExercice = Number(idExerciceParam);
      const idTopic = Number(idTopicParam);

      this.exerciceService
        .updateExercice(idCourse, idTopic, idExercice, this.exercice)
        .subscribe({
          next: () => {
            this.router.navigate([`/course/${idCourse}/topic/${idTopic}`]);
          },
          error: (error) => {
            console.error('Error updating exercice:', error);
          },
        });
    } else {
      console.error(
        'Course ID, Exercice ID or Topic ID is missing in the route parameters.'
      );
    }
  }
}
