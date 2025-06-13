# Exercice Entity :
---

- Cette entity est comme les autres juste a quelques exceptions près :

```java
@Column(name = "feynman_status")
@Enumerated(EnumType.STRING)
private FeynmanStatus feynmanStatus = FeynmanStatus.NOT_STARTED;
```
- ici feynmanStatus à une valeur par defaut quand on créer un objet et proviens d'une Enum qui contient plusieurs état pour le status d'un exercice.
on met @Enumerated(EnumType.STRING) pour dire explicitement a la db que les éléments contenus dans l'enum sont des string et la colonne sera donc de ce type.
