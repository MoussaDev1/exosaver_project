# Entity/Model Topic :
---

### Dans cette entity tout est normal jusqu'au moment ou il y a :
```
@ManyToOne(optional = false)
@JoinColumn(name = "course_id", nullable = false)
private Course course;
```
#### @ManyToOne(optional = false) :
Indique le type de relation entre les différente table, dans ce cas si c'est plusieurs vers 1(plusieurs topic ne peuvent appartenir qu'a un seul cours).(optional = false/true signifie que la clé étrangère ne peut pas etre null dans java(dans l'objet) est doit obligatoirement etre definit avec un Course (son @id))

---

#### @JoinColumn(name = "course_id", nullable = false)
private Course course; :
Permet de definir a quoi correspond la clé étrangère dans l'entity et dire que l'on lie la clé étrangère a la clé primaire de la objet définit, Course dans ce cas(on vas lier course_id a l'id de la entity course) (nullable = true/false permet de dire si la valeur de la clé étrangère peut être null ou non en sql)

