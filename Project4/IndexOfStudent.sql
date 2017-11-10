-- Index: IndexOfStudent

-- DROP INDEX project4."IndexOfStudent";

CREATE INDEX "IndexOfStudent"
    ON project4.student USING btree
    (id, name COLLATE pg_catalog."default", address COLLATE pg_catalog."default", status COLLATE pg_catalog."default")
    TABLESPACE pg_default;