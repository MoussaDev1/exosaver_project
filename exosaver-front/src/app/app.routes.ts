import { Routes } from '@angular/router';
import { CreateCourseComponent } from './Course/create-course-component/create-course-component';
import { CoursesComponent } from './Course/courses-component/courses-component';
import { OneCourseComponent } from './Course/one-course-component/one-course-component';
import { EditCourseComponent } from './Course/edit-course-component/edit-course-component';

export const routes: Routes = [
  { path: '', component: CoursesComponent },
  { path: 'create', component: CreateCourseComponent },
  { path: 'course/:id', component: OneCourseComponent },
  { path: 'course/edit/:id', component: EditCourseComponent },
];
