
# Méthode : `updateFeynmanStatus`

## ✅ Objectif de la méthode
Mettre à jour le `feynmanStatus` d'un exercice après qu'un utilisateur ait évalué sa compréhension d'un concept, via la méthode Feynman.

---

## ✅ Paramètres attendus
- `exerciseId` : l'identifiant de l'exercice concerné.
- `topicId` : l'identifiant du topic auquel appartient l'exercice.
- `courseId` : l'identifiant du cours auquel est censé appartenir le topic.
- `newStatus` : la valeur de l'énumération `FeynmanStatus` à appliquer (envoyée par l'utilisateur).

---

## ✅ Déroulement de la méthode

### ✉️ Étape 1 : Vérifications préalables
- On vérifie que le `topic` existe.
- On vérifie que ce topic est bien lié au `course` donné.
- On vérifie que l'`exercise` existe bien **et est lié au bon topic**.

### ✉️ Étape 2 : Mise à jour du statut
- On applique à l'exercice le `newStatus` donné par l'utilisateur :
  ```java
  exercise.setFeynmanStatus(newStatus);
  ```

### ✉️ Étape 3 : Logique de révision espacée

- Si `newStatus` vaut `EXPLAINED_OK` ou `REVIEW_OK` :
    - On incrémente `feynmanSuccessCount`.
    - En fonction du nombre de réussites, on programme la prochaine révision à :

      | Compteur | Délai (jours) |
          |----------|---------------|
      | 1        | 3             |
      | 2        | 6             |
      | 3        | 10            |
      | 4+       | 15            |

- Sinon (autre statut) :
    - On remet le compteur à 0
    - Et la prochaine révision est planifiée pour le lendemain

### ✉️ Étape 4 : Sauvegarde
- L'exercice mis à jour est sauvegardé dans la base via :
  ```java
  exerciseRepository.save(exercise);
  ```

---

## ✅ Exceptions personnalisées utilisées
- `TopicNotFoundException`
- `TopicNotInCourseException`
- `ExerciseNotFoundException`

---

## ✅ Exemple d'appel dans le contrôleur
```java
@PutMapping("/{id}/feynman")
public ResponseEntity<Void> updateFeynmanStatus(
    @PathVariable Long id,
    @RequestBody FeynmanEvaluationRequestDTO dto
) {
    exerciseService.updateFeynmanStatus(id, dto.getTopicId(), dto.getCourseId(), dto.getFeynmanStatus());
    return ResponseEntity.noContent().build();
}
```

---

## ✅ Avantages pédagogiques
- Suivi personnalisé de la progression conceptuelle par exercice.
- Mécanisme d'oubli espacé implémenté de manière simple et adaptée à la méthode Feynman.
- Clarté du code grâce à la séparation entre la vérification des données et la logique d'apprentissage.
