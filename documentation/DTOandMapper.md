
# 📦 Structuration des échanges dans une API Spring Boot

Ce document regroupe 3 concepts fondamentaux pour bien structurer ton backend :

- ✅ DTO (Data Transfer Object)
- ✅ Mapper
- ✅ Validation (`@Valid`, `@NotNull`, etc.)

---

## 1. ✉️ DTO (Data Transfer Object)

### 🧠 Définition

Un DTO est une classe utilisée pour transférer des données entre le client et le serveur **sans exposer toute la structure interne de l'application**.

> On utilise les DTO pour séparer ce que l’utilisateur peut envoyer ou recevoir de ce qu’on utilise réellement en base de données (l'entité).

---

### 📦 Deux types de DTO

- **RequestDTO** : données que l’utilisateur est autorisé à envoyer (création, modification).
- **ResponseDTO** : données que l’on renvoie à l’utilisateur (lecture, affichage).

---

### ✅ Pourquoi les utiliser ?

| Raison          | Avantage                                                |
|-----------------|---------------------------------------------------------|
| 🔒 Sécurité     | Évite d'exposer des champs sensibles (ex : mot de passe) |
| 🧹 Propreté     | Sépare l’API de la logique métier et des entités        |
| 🧪 Validation   | Permet de valider facilement les données entrantes      |
| 🔧 Flexibilité  | Tu peux formater ou adapter ce que tu exposes           |

---

## 2. 🔁 Mapper

### 🧠 Définition

Un **Mapper** est une classe utilitaire chargée de convertir un objet d’un type à un autre, souvent entre **Entity et DTO**.

---

### ✨ Exemple manuel :

```java
public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
```

---

### 🧠 Bonnes pratiques

- Méthodes **statiques**
- Pas de logique métier dedans
- Utilisé **dans le service**, pas dans le contrôleur

---

### 🤖 Option avancée : MapStruct

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);
    UserResponseDTO toResponseDTO(User user);
}
```

---

## 3. 🛡️ Validation des données

### 🧠 Définition

La validation permet de **contrôler automatiquement** les données envoyées par l’utilisateur.  
Elle se fait directement dans les `RequestDTO`.

---

### ✅ Exemples d’annotations utiles

```java
public class UserRequestDTO {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    private String password;
}
```

---

### 🔧 Utilisation dans le contrôleur

```java
@PostMapping
public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO dto) {
    ...
}
```

Le mot-clé `@Valid` déclenche automatiquement la validation des champs du DTO.

---

### 🚨 Gestion des erreurs

Spring renverra automatiquement une erreur 400 si un champ invalide est détecté.  
Tu peux personnaliser ça avec un `@ControllerAdvice`.

---

## ✅ Résumé global

| Élément     | Rôle principal                          |
|-------------|------------------------------------------|
| DTO         | Encadre les données envoyées/reçues     |
| Mapper      | Convertit DTO ↔️ Entity                  |
| Validation  | Vérifie que les données sont correctes  |

---

Garder ces trois outils bien structurés, c’est garantir un code propre, maintenable et sécurisé.
