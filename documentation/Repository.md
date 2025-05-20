# Répository Explication :

- On Créer une interface qui extends JpaRepository<T, ID> dans JpaRepository il y a les méthodes prêtent à l'emploi qui permettent d'éffectuer des opération pour intéragir avec la base de donnée, en utilisant un type qui correspond a une Entity et un autre type qui correspond a un ID(le type de la clé primaire).
---

### La logique derrière ça :
- On crée une interface (ex : UserRepository) qui hérite de JpaRepository.
Cette interface déclare uniquement les méthodes qu’on veut utiliser (ex : findById, save, etc.),
sans définir la logique de ces méthodes.


- Quand on lance l’application, Spring analyse cette interface,
et crée automatiquement une classe “éphémère” (proxy dynamique)
qui implémente l’interface et contient la vraie logique des méthodes (ex : les requêtes SQL, les transactions, etc.).


- Donc quand on appelle une méthode comme userRepository.findById(1L),
c’est la classe proxy cachée qui est exécutée en réalité — pas l’interface.
---
## JPA Query Methods

- La particularité de ce repository c'est que l'on a des des méthodes ajouter manuellement en plus de celle de JpaRepository. Mais c'est méthodes ne sont pas définit on les nommes juste et on leurs donne les paramètres nécessaire a leurs fonctionne et spring data jpa vas nous générer la logique du code et nous permettre de l'utiliser sans avoir besoin d'écrire des lignes de codes pour la logique de ceux-ci.

## Logique derriere ça :
- Spring Data JPA peut générer automatiquement une requête SQL juste en analysant le nom de la méthode que tu déclares dans ton repository.
  Tu n’as pas besoin d’écrire la logique, tant que tu respectes les conventions (findBy..., And, Or, etc.).
  Cela fonctionne parce que Spring utilise un moteur de parsing de nom de méthode pour créer dynamiquement les requêtes.