# 🧠 TopicService – Explication détaillée

## 🔹 Pourquoi utilise-t-on `CourseRepository` et `TopicRepository` dans cette classe ?

Car ils permettent d’effectuer des opérations avec la base de données en utilisant `extends JpaRepository<Course, Long>` ou `JpaRepository<Topic, Long>`, avec en paramètre une entité sur laquelle on souhaite exécuter des opérations (recherche, sauvegarde, suppression, etc.).

Dans ce cas, l’injection de dépendance est faite par **constructeur**, ce qui revient au même que `@Autowired`, mais c’est une **meilleure pratique** car elle améliore la **testabilité** et la **lisibilité du code**.

---

## 🛠️ createTopic - Explication

```java
public TopicResponseDTO createTopic(Long courseId, TopicRequestDTO dto) {
    Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new CourseNotFoundException(courseId));

    Topic topic = TopicMapper.toEntity(dto);
    topic.setCourse(course);
    topic = topicRepository.save(topic);

    return TopicMapper.toResponse(topic);
}
```

1. On retourne un `TopicResponseDTO` pour renvoyer à l'utilisateur les informations de manière **contrôlée et sécurisée**.  
   Les paramètres sont `(Long courseId, TopicRequestDTO dto)` :
    - `courseId` : identifie le cours dans lequel on veut créer un topic.
    - `dto` : contient les données envoyées par l’utilisateur pour le nouveau topic.

2. On crée une variable `course` de type `Course`, dans laquelle on exécute `courseRepository.findById(courseId)` pour retrouver le cours cible.

3. Ensuite, on crée une variable `topic` de type `Topic` et on exécute `TopicMapper.toEntity(dto)` pour **transformer la requête utilisateur en entité**.

4. On lie ce topic au cours en utilisant `topic.setCourse(course)`.

5. On enregistre ensuite le topic dans la base avec `topicRepository.save(topic)`.

6. On retourne ensuite le `TopicResponseDTO` généré à partir de l’entité, **filtré et sécurisé**.

---

## 🔍 getTopicById - Explication

```java
public TopicResponseDTO getTopicById(Long courseId, Long topicId) {
    Topic topic = topicRepository.findByIdAndCourseId(topicId, courseId)
        .orElseThrow(() -> new TopicNotFoundException(topicId));
    return TopicMapper.toResponse(topic);
}
```

1. On retourne un `TopicResponseDTO` pour envoyer les données du topic à l'utilisateur de manière **filtrée**.  
   Paramètres : `(Long courseId, Long topicId)` pour identifier à la fois le cours et le topic.

2. On exécute `topicRepository.findByIdAndCourseId(topicId, courseId)` pour **s’assurer que le topic appartient bien au bon cours**.
   > ⚠️ Cette méthode n’est pas fournie par JPA par défaut : elle est déclarée dans `TopicRepository` et Spring génère la requête automatiquement à partir du nom.

3. On retourne ensuite un `TopicResponseDTO` filtré.

---

## 📋 getAllTopics - Explication

```java
public List<TopicResponseDTO> getAllTopics(Long courseId) {
    List<Topic> topics = topicRepository.findByCourseId(courseId);
    return topics.stream().map(TopicMapper::toResponse).toList();
}
```

Même principe que dans `CourseService` : on récupère tous les topics d’un cours et on retourne une liste de `TopicResponseDTO`. sauf que l'on utilise la méthode findByCourseId.

---

## ✏️ updateTopic - Explication

```java
public TopicResponseDTO updateTopic(Long courseId, Long topicId, TopicRequestDTO dto) {
    Topic topic = topicRepository.findByIdAndCourseId(topicId, courseId)
        .orElseThrow(() -> new TopicNotFoundException(topicId));

    topic.setTitle(dto.getTitle());
    topic.setDescription(dto.getDescription());

    return TopicMapper.toResponse(topicRepository.save(topic));
}
```

1. On retourne un `TopicResponseDTO` pour **mettre à jour et renvoyer les données** du topic de manière contrôlée.  
   Paramètres : `(Long topicId, Long courseId, TopicRequestDTO dto)`.

2. On exécute `topicRepository.findByIdAndCourseId(topicId, courseId)` pour récupérer le topic **et vérifier qu’il appartient bien au cours ciblé**.  
   Ensuite, on met à jour les champs du topic via `topic.setXXX(dto.getXXX())`.

3. On retourne un `TopicResponseDTO` filtré et sécurisé.

---

## 🗑️ deleteTopic - Explication

```java
public void deleteTopic(Long courseId, Long topicId) {
    Topic topic = topicRepository.findByIdAndCourseId(topicId, courseId)
        .orElseThrow(() -> new TopicNotFoundException(topicId));
    topicRepository.delete(topic);
}
```

Même logique que dans `CourseService`, à la différence que **le `courseId` est passé en paramètre** pour s’assurer que l’on supprime bien le topic lié au bon cours.