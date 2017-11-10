-- Index: IndexOfTranscript

-- DROP INDEX project4."IndexOfTranscript";

CREATE INDEX "IndexOfTranscript"
    ON project4.transcript USING btree
    (studid, crscode COLLATE pg_catalog."default", semester COLLATE pg_catalog."default", grade COLLATE pg_catalog."default")
    TABLESPACE pg_default;