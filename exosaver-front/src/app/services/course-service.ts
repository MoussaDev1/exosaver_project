import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Courses } from '../models/courses';
import { Course } from '../models/course'; // Assuming you have a model for course

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private apiUrl = 'http://localhost:8080/api/courses';
  private apiUrl2 = 'http://localhost:8080/api/course';

  constructor(private http: HttpClient) {}

  /*On utilise Observable pour que le composant puisse s'abonner aux changements
   * et recevoir les données de manière asynchrone.
   * Cette méthode permet de récupérer tous les cours depuis l'API.
   * Elle utilise HttpClient pour envoyer une requête GET à l'URL spécifiée.
   * @return {Observable<any>} Un observable qui émet les données des cours.
   */
  getAllCourses(): Observable<Courses[]> {
    return this.http.get<Courses[]>(`${this.apiUrl}`);
  }

  createCourse(course: Course): Observable<Courses> {
    return this.http.post<Courses>(`${this.apiUrl}`, course);
  }

  getCourseById(id: number): Observable<Courses> {
    return this.http.get<Courses>(`${this.apiUrl2}/${id}`);
  }

  updateCourse(id: number, course: Course): Observable<Courses> {
    return this.http.put<Courses>(`${this.apiUrl2}/${id}`, course);
  }

  deleteCourse(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl2}/${id}`);
  }
}
