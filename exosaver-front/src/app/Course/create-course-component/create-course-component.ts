import { Component } from '@angular/core';
import { FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { Course } from '../../models/course';
import { CourseService } from '../../services/course-service';
import { Courses } from '../../models/courses';
import { RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-create-course-component',
  imports: [
    FormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatStepperModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatCardModule,
  ],
  templateUrl: './create-course-component.html',
  styleUrl: './create-course-component.scss',
})
export class CreateCourseComponent {
  course: Course = {
    title: '',
    description: '',
    objectives: '',
    themes: '',
  };
  courses: Courses[] = [];

  courseDraft = {
    title: '',
    description: '',
    objectives: '',
    themes: '',
  };

  constructor(private courseService: CourseService) {}

  updateTtitle(newTitle: string) {
    this.courseDraft.title = newTitle;
  }
  updateDescription(newDescription: string) {
    this.courseDraft.description = newDescription;
  }
  updateObjectives(newObjectives: string) {
    this.courseDraft.objectives = newObjectives;
  }
  updateThemes(newThemes: string) {
    this.courseDraft.themes = newThemes;
  }

  onSubmit(form: NgForm): void {
    this.courseService.createCourse(this.course).subscribe({
      next: (newCourse) => {
        this.courses.push(newCourse);
        form.resetForm();
      },
    });
  }
}
