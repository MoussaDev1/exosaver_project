import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Course } from '../../models/course';
import { CourseService } from '../../services/course-service';
import { Courses } from '../../models/courses';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-create-course-component',
  imports: [FormsModule, RouterLink],
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

  constructor(private courseService: CourseService) {}

  onSubmit(form: NgForm): void {
    this.courseService.createCourse(this.course).subscribe({
      next: (newCourse) => {
        this.courses.push(newCourse);
        form.resetForm();
      },
    });
  }
}
