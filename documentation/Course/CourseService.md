
# CourseService - Explication

---

## createCourse Explication :

```java
public CourseResponseDTO createCourse(CourseRequestDTO dto){
    Course course = CourseMapper.toEntity(dto);
    course = courseRepository.save(course);
    return CourseMapper.toResponseDTO(course);
}
```

1. On fait retourner la classe avec `CourseResponseDTO` pour avoir une réponse de ce qui a été créé, et on met en paramètre `CourseRequestDTO dto` pour pouvoir récupérer la requête de l'utilisateur.
2. On crée une variable `course` de type `Course` et on l’assigne à `CourseMapper.toEntity(dto)`. Cela sert à convertir la requête de l'utilisateur (`CourseRequestDTO dto`) en Entity (model) pour que SpringJPA puisse interagir avec (la sauvegarder en base de données dans ce cas-là).
3. Maintenant que `course` (converti en Entity) est créé dans la fonction, on peut le sauvegarder en base de données avec `courseRepository.save(course)`.
4. On retourne ensuite `CourseMapper.toResponseDTO(course)` pour envoyer une réponse filtrée à l'utilisateur avec `toResponseDTO` qui convertit une Entity (model) en DTO.

👉 Pour l’explication du DTO, aller dans `/docs/Feynman/DTO`

---

## getCoursesById Explication :

```java
public CourseResponseDTO getCoursesById(Long id){
    Course course = courseRepository.findById(id)
            .orElseThrow(() -> new CourseNotFoundException(id));
    return CourseMapper.toResponseDTO(course);
}
```

1. On fait retourner la classe avec `CourseResponseDTO` pour avoir une réponse du `Course` demandé, et on met en paramètre `Long id` pour pouvoir récupérer l'id correspondant au `Course` recherché.
2. On crée une variable `course` de type `Course` et on exécute une méthode de `courseRepository` (`.findById(id).orElseThrow()`). Ce qui se passe, c’est que : à la base, `findById(id)` est censé retourner un `Optional<Course>`, ce qui est ≠ de `Course`. La fonction `orElseThrow()` permet de directement récupérer le `Course` s’il existe, ou de lancer une exception sinon. Ce qui nous permet d’avoir un type `Course` dans la variable `course` au lieu de `Optional<Course>`.
3. On retourne ensuite `CourseMapper.toResponseDTO(course)` pour envoyer une réponse filtrée à l'utilisateur avec les éléments du `Course` qu’il demandait, tout en étant filtrée grâce au DTO.

---

## getAllCourses Explication :

```java
public List<CourseResponseDTO> getAllCourses(){
    List<Course> courses = courseRepository.findAll();
    return courses.stream().map(CourseMapper::toResponseDTO).toList();
}
```

1. On fait retourner une `List` de `CourseResponseDTO` pour avoir tous les `Courses` qui ont été créés.
2. On crée une variable `courses` de type `List<Course>` et on exécute `courseRepository.findAll()` à l’intérieur de cette variable.
3. On retourne ensuite la liste `courses` sous forme de `stream()` pour pouvoir la manipuler plus facilement avec des fonctions spécifiques (comme `map` ici), et on utilise `map` pour justement utiliser la méthode `toResponseDTO` sur tous les éléments de la liste `courses`. Grâce à `map`, chaque élément de la liste devient un `CourseResponseDTO` (toujours sous forme de `stream`), et on utilise la méthode `toList()` pour avoir une `List<CourseResponseDTO>` au final.

---

## updateCourse Explication :

```java
public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto){
    Course course = courseRepository.findById(id)
            .orElseThrow(() -> new CourseNotFoundException(id));
    course.setTitle(dto.getTitle());
    course.setDescription(dto.getDescription());
    course.setObjectives(dto.getObjectives());
    course.setThemes(dto.getThemes());
    Course updatedCourse = courseRepository.save(course);
    return CourseMapper.toResponseDTO(updatedCourse);
}
```

1. On fait retourner une classe `CourseResponseDTO` pour pouvoir avoir une réponse de ce qui a été modifié, et en paramètre on a `Long id` et `CourseRequestDTO dto` pour identifier quel `Course` il faut modifier et pour récupérer les changements effectués par l'utilisateur (envoyés via un formulaire par exemple).
2. On crée une variable `course` de type `Course` et on exécute `courseRepository.findById(id).orElseThrow()` à l'intérieur (rappel : initialement `findById` retourne un objet de type `Optional<Course>` mais `.orElseThrow()` permet de directement vérifier si le `Course` récupéré existe, donc on peut directement le manipuler en tant qu'objet `Course`).
3. Ensuite, on assigne les valeurs modifiées à l’Entity via `CourseRequestDTO` qui contient les valeurs de la requête de l'utilisateur.
4. On crée ensuite une variable `updatedCourse` de type `Course` qui va enregistrer en base de données les modifications effectuées via `.save()`.
5. Et l’on retourne un `CourseResponseDTO` avec les modifications qui ont été effectuées, tout en filtrant les données à renvoyer comme toujours.

---

## deleteCourse Explication :

```java
public void deleteCourse(Long id){
    if(!courseRepository.existsById(id)){
        throw new CourseNotFoundException(id);
    }
    courseRepository.deleteById(id);
}
```

1. On ne retourne rien à l'utilisateur car on supprime un élément, donc on n’a rien à lui retourner comme des éléments d’un DTO. On a en paramètre `Long id` pour identifier le `Course` que l’on veut supprimer.
2. On vérifie que le `Course` existe bien via `existsById(id)`.
3. S’il existe, alors on le supprime.

---
