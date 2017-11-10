/*
 * List the name of the student with id equal to v1 (id).
 */
SET @v1 = X;

SELECT s.name
FROM student as s
WHERE s.id = @v1;