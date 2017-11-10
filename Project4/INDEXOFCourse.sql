-- Index: INDEXOFCourse

-- DROP INDEX project4."INDEXOFCourse";

CREATE INDEX "INDEXOFCourse"
    ON project4.course USING btree
    (crscode COLLATE pg_catalog."default", deptid COLLATE pg_catalog."default", crsname COLLATE pg_catalog."default", descr COLLATE pg_catalog."default")
    TABLESPACE pg_default;