# @TopicRepository
---

- La particularité de ce repository c'est que l'on a des des méthodes ajouter manuellement en plus de celle de JpaRepository. Mais c'est méthodes ne sont pas définit on les nommes juste et on leurs donne les paramètres nécessaire a leurs fonctionne et spring data jpa vas nous générer la logique du code et nous permettre de l'utiliser sans avoir besoin d'écrire des lignes de codes pour la logique de ceux-ci.

## Logique derriere ça :
- Spring Data JPA peut générer automatiquement une requête SQL juste en analysant le nom de la méthode que tu déclares dans ton repository.
Tu n’as pas besoin d’écrire la logique, tant que tu respectes les conventions (findBy..., And, Or, etc.).
Cela fonctionne parce que Spring utilise un moteur de parsing de nom de méthode pour créer dynamiquement les requêtes.

## List<Topic> findByCourseId(Long courseId) :
- Fait : Donne moi la liste de tout les Topic qui on pour courseId la valeur donnée en paramètre.

## Optional<Topic> findByIdAndCourseId(Long topicId, Long courseId) :
- Fait : DOnne moi la donnée correspondant au topicId et courseId donnée en paramètre

