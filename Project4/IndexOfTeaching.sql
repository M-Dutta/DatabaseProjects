-- Index: IndexOfTeaching

-- DROP INDEX project4."IndexOfTeaching";

CREATE INDEX "IndexOfTeaching"
    ON project4.teaching USING btree
    (crscode COLLATE pg_catalog."default", semester COLLATE pg_catalog."default", profid)
    TABLESPACE pg_default;
