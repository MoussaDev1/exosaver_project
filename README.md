# 🎓 ExoSaver API

Bienvenue dans l’API **ExoSaver**, une application d’apprentissage par exercices avec suivi Feynman.  
Elle permet de gérer des cours, des topics, des exercices, des ressources, et d’évaluer la compréhension avec la méthode Feynman. Et en ayant des cours espacé pour renforcé la mémorisation des apprentissages

---

## 📦 Architecture REST

### 🔹 Cours

| Action              | Méthode | Endpoint               |
|---------------------|---------|------------------------|
| Créer un cours      | POST    | `/api/courses`         |
| Liste des cours     | GET     | `/api/courses`         |
| Détails d’un cours  | GET     | `/api/course/{id}`     |
| Modifier un cours   | PUT     | `/api/course/{id}`     |
| Supprimer un cours  | DELETE  | `/api/course/{id}`     |

---

### 🔹 Topics (chapitres d’un cours)

| Action                | Méthode | Endpoint                                     |
|-----------------------|---------|----------------------------------------------|
| Créer un topic        | POST    | `/api/course/{coursId}/topics`              |
| Liste des topics      | GET     | `/api/course/{coursId}/topics`              |
| Détail d’un topic     | GET     | `/api/course/{coursId}/topic/{id}`          |
| Modifier un topic     | PUT     | `/api/course/{coursId}/topic/{id}`          |
| Supprimer un topic    | DELETE  | `/api/course/{coursId}/topic/{id}`          |

---

### 🔹 Exercices

| Action                | Méthode | Endpoint                                                                 |
|-----------------------|---------|--------------------------------------------------------------------------|
| Créer un exercice     | POST    | `/api/course/{coursId}/topic/{topicId}/exercices`                       |
| Liste des exercices   | GET     | `/api/course/{coursId}/topic/{topicId}/exercices`                       |
| Détail d’un exercice  | GET     | `/api/course/{coursId}/topic/{topicId}/exercice/{id}`                   |
| Modifier un exercice  | PUT     | `/api/course/{coursId}/topic/{topicId}/exercice/{id}`                   |
| Supprimer un exercice | DELETE  | `/api/course/{coursId}/topic/{topicId}/exercice/{id}`                   |

---

### 🔹 Méthode Feynman

| Action                          | Méthode | Endpoint                                                                    |
|---------------------------------|---------|-----------------------------------------------------------------------------|
| Mettre à jour le statut Feynman | PUT     | `/api/course/{coursId}/topic/{topicId}/exercice/{id}/feynman`              |

---

### 🔹 Ressources

| Action                                | Méthode | Endpoint                                                                 |
|---------------------------------------|---------|--------------------------------------------------------------------------|
| Ajouter à un cours                    | POST    | `/api/course/{courseId}/resources`                                       |
| Ajouter à un exercice                 | POST    | `/api/course/{courseId}/topics/{topicId}/exercises/{exerciseId}/resources` |
| Lister ressources d’un cours          | GET     | `/api/course/{courseId}/resources`                                       |
| Lister ressources d’un exercice       | GET     | `/api/course/{courseId}/topics/{topicId}/exercises/{exerciseId}/resources` |
| Détail d’une ressource (globale)      | GET     | `/api/resource/{id}`                                                     |
| Modifier une ressource                | PUT     | `/api/resource/{id}`                                                     |
| Supprimer une ressource               | DELETE  | `/api/resource/{id}`                                                     |

---

## ✅ Format des données (exemple)

### 🔸 Création d’un exercice

---

## 🧠 Suivi Feynman

- Lorsqu’un exercice est expliqué par l’utilisateur, il peut mettre à jour son `feynmanStatus` via un PUT.
- Le backend gère automatiquement le prochain rappel (`nextReviewDate`) selon le nombre de réussites (`feynmanSuccessCount`).

---

## 🚀 À venir

- Authentification utilisateur
- DashBoard
- Gestion de progression détaillée
- Ajout d'une interface angular (en cours)
