import { exercices } from './../models/exercices';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FeynmanStatus } from '../models/feynmanStatus';

@Injectable({
  providedIn: 'root',
})
export class ExercicesService {
  private apiUrl = 'http://localhost:8080/api/course';

  constructor(private http: HttpClient) {}

  createExercicesByTopicId(
    exercice: exercices,
    idCourse: number,
    idTopic: number
  ): Observable<exercices> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}/exercices`;
    return this.http.post<exercices>(url, exercice);
  }

  getAllExercicesByTopicId(
    idCourse: number,
    idTopic: number
  ): Observable<exercices[]> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}/exercices`;
    return this.http.get<exercices[]>(url);
  }

  getExerciceById(
    idCourse: number,
    idTopic: number,
    idExercice: number
  ): Observable<exercices> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}/exercice/${idExercice}`;
    return this.http.get<exercices>(url);
  }

  updateExercice(
    idCourse: number,
    idTopic: number,
    idExercice: number,
    exercice: exercices
  ): Observable<exercices> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}/exercice/${idExercice}`;
    return this.http.put<exercices>(url, exercice);
  }

  deleteExercice(
    idCourse: number,
    idTopic: number,
    idExercice: number
  ): Observable<void> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}/exercice/${idExercice}`;
    return this.http.delete<void>(url);
  }

  updateFeynmanStatus(
    idExercice: number,
    idTopic: number,
    idCourse: number,
    feynmanStatus: FeynmanStatus
  ): Observable<FeynmanStatus> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}/exercice/${idExercice}/feynman`;
    return this.http.put<FeynmanStatus>(url, feynmanStatus);
  }
}
