import { Routes } from '@angular/router';
import { CreateCourseComponent } from './Course/create-course-component/create-course-component';
import { CoursesComponent } from './Course/courses-component/courses-component';
import { OneCourseComponent } from './Course/one-course-component/one-course-component';
import { EditCourseComponent } from './Course/edit-course-component/edit-course-component';
import { OneTopicsComponent } from './Topic/one-topics-component/one-topics-component';
import { EditTopicComponent } from './Topic/edit-topic-component/edit-topic-component';
import { CreateTopicComponent } from './Topic/create-topic-component/create-topic-component';

export const routes: Routes = [
  { path: '', component: CoursesComponent },
  { path: 'create', component: CreateCourseComponent },
  { path: 'course/:idCourse', component: OneCourseComponent },
  { path: 'course/edit/:idCourse', component: EditCourseComponent },
  {
    path: 'course/:idCourse/topics/:idTopic',
    component: OneTopicsComponent,
  },
  {
    path: 'course/:idCourse/topic/edit/:idTopic',
    component: EditTopicComponent,
  },
  { path: 'course/:idCourse/topic/create', component: CreateTopicComponent },
];
