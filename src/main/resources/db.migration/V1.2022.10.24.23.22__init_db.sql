create table person (
    person_id UUID NOT NULL,
    username VARCHAR(30),
    password VARCHAR(255),
    email VARCHAR(255),
    age INT8,
    PRIMARY KEY (person_id)
);

create table notes (
    id UUID NOTNULL,
    header VARCHAR(255),
    data_create DATE,
    data_update DATE,
    text_notes VARCHAR(255),
    person_id UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES person(person_id)
);
