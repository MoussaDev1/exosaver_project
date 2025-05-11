
-- TABLE: Course
CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    objectives TEXT,
    themes TEXT
);

-- TABLE: Topic
CREATE TABLE topic (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    course_id INTEGER NOT NULL,
    CONSTRAINT fk_topic_course FOREIGN KEY (course_id)
        REFERENCES course(id) ON DELETE CASCADE
);

-- TABLE: Exercise
CREATE TABLE exercise (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    solution TEXT,
    status VARCHAR(50),
    topic_id INTEGER NOT NULL,
    feynman_status VARCHAR(50),
    next_review_date TIMESTAMP,
    CONSTRAINT fk_exercise_topic FOREIGN KEY (topic_id)
        REFERENCES topic(id) ON DELETE CASCADE
);

-- TABLE: Resource
CREATE TABLE resource (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    type VARCHAR(50),
    course_id INTEGER,
    exercise_id INTEGER,
    CONSTRAINT fk_resource_course FOREIGN KEY (course_id)
        REFERENCES course(id) ON DELETE SET NULL,
    CONSTRAINT fk_resource_exercise FOREIGN KEY (exercise_id)
        REFERENCES exercise(id) ON DELETE SET NULL
);
