# Dto(Data Transfer Object) explication :
- Les dto sont des classes qui permet de créer des réponses à renvoyer a l'utilisateurs en omettant des valeurs que l'on veut garder priver sans exposer directement l'entité complète (mot de passe, clé étrangère etc) Et permet de contrôler les données échangeés. :
---
## Comment ça fonctionne :
1. On créer d'abord les classes RequestDTO et ResponseDTO qui correspondront à la classe dont il faudra filtré les données. La classe RequestDTO doit correspondre aux données que l'utilisateur vas envoyé (via formulaire par exemple) donc sans l'id et ou clé ou autre. Et ResponseDTO correspondra à la classe qui contiendra les données qui seront renvoyer a l'utilisateur tout en étant filtré(sans les données sensible).


2. Pour faire ça on utilise un Mapper (qui signifie transformé un objet d'un certain type à un autre). Le Mapper permet de transformé une Entity/Model en Une ResponseDTO (via la requête en paramètre) et de transformé une requête en une Entity/Model. Pour que celle-ci soit enregistrer dans la base de donnée et créer avec les attributs en plus dont elle aura besoin (comme la clé primaire ou un mdp).


3. Donc par exemple si on veut créer un user :
L'utilisateur vas remplir un formulaire avec ses donnée dedans et vas faire une requête pour que celle ci soit exécuter.
Une fois la requête récupérer on utilise toEntity qui vas récuperer les données du formulaire via RequestDTO qu'il à en paramètre pour créer un objet Course qui sera enregistrer en base de donnée.
Une fois celle ci enregistrer en base de donnée disons que l'utilisateur veuille récupérer La liste de tout les utilisateur il vas faire findAll(), donc on vas utiliser la méthode toResponseDTO qui vas d'abord récuperer un Objet de type User et assigner les valeurs de celui-ci à ResponseDTO et celui ci vas ensuite être envoyé la utilisateur mais en étant filtrer car le DTO ne contiendra que les valeurs que l'on veut retourner.

Et du coup si un utilisateur essaye de faire une requête avec des valeurs que l'on ne veut pas par des moyen externe au formulaire il ne pourras pas car la requête passe par le DTO qui lui ne permettra pas d'inserer des valeurs que l'on ne veut pas que l'utilisateur manipule donc il ne se passera rien ou ça requête échoueras tout simplement.