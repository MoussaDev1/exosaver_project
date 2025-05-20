# Les controllers :

---

- Un controller est une classe qui permet de faire la transition entre le back-end et le front-end. Il reçoit les requête via le front end (HTTP ou Api externe) et le controller les reçois justement et permet d'effectuer des actions grâce à la logique métiers du service que est utilser dans les méthodes HTTP du controller qui ont été envoyé et renvoyé une réponse.

## Utilisation avec Spring Boot :
- @RestController est une annotation qui permet d'identifier une classe comme étant un controller et lui permettre d'être détecter pour récuperer des données.
- @RequestMapping est une annotation qui permet de definir une URL de base pour tout les Endpoint de chaque méthode HTTP qui sera utiliser.
- @PathVariable sert a identifier une valeurs dans l'url (dans la plupart des cas pour l'id).
- @RequestBody sert a récuperer des information JSON envoyé dans le body d'une requête http et la traduire en objet java comprehensible par spring boot et l'inject dans la méthode mis en paramètre avec cette annotation.