-- Index: IndexOfProfessor

-- DROP INDEX project4."IndexOfProfessor";

CREATE INDEX "IndexOfProfessor"
    ON project4.professor USING btree
    (id, name COLLATE pg_catalog."default", deptid COLLATE pg_catalog."default")
    TABLESPACE pg_default;
