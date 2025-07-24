import { Routes } from '@angular/router';
import { CreateCourseComponent } from './Course/create-course-component/create-course-component';
import { CoursesComponent } from './Course/courses-component/courses-component';
import { OneCourseComponent } from './Course/one-course-component/one-course-component';
import { EditCourseComponent } from './Course/edit-course-component/edit-course-component';
import { OneTopicsComponent } from './Topic/one-topics-component/one-topics-component';
import { EditTopicComponent } from './Topic/edit-topic-component/edit-topic-component';
import { CreateTopicComponent } from './Topic/create-topic-component/create-topic-component';
import { OneExerciceComponent } from './Exercice/one-exercice-component/one-exercice-component';
import { CreateExerciceComponent } from './Exercice/create-exercice-component/create-exercice-component';
import { EditExerciceComponent } from './Exercice/edit-exercice-component/edit-exercice-component';

export const routes: Routes = [
  { path: '', component: CoursesComponent, data: { breadcrumb: 'Courses' } },
  {
    path: 'create',
    component: CreateCourseComponent,
    data: { breadcrumb: 'Create Course' },
  },
  {
    path: 'course/:idCourse',
    component: OneCourseComponent,
    data: { breadcrumb: 'Course Details' },
  },
  {
    path: 'course/edit/:idCourse',
    component: EditCourseComponent,
    data: { breadcrumb: 'Edit Course' },
  },
  {
    path: 'course/:idCourse/topic/create',
    component: CreateTopicComponent,
    data: { breadcrumb: 'Create Topic' },
  },
  {
    path: 'course/:idCourse/topic/:idTopic',
    component: OneTopicsComponent,
    data: { breadcrumb: 'Sujet Détails' },
  },

  {
    path: 'course/:idCourse/topic/edit/:idTopic',
    component: EditTopicComponent,
  },

  {
    path: 'course/:idCourse/topic/:idTopic/exercice/:idExercice',
    component: OneExerciceComponent,
    data: { breadcrumb: 'Exercice Détails' },
  },

  {
    path: 'course/:idCourse/topic/:idTopic/exercices/create',
    component: CreateExerciceComponent,
  },

  {
    path: 'course/:idCourse/topic/:idTopic/exercice/edit/:idExercice',
    component: EditExerciceComponent,
  },
];
