/*
 * List the names of students with id in the range of v2 (id) to v3 (inclusive).
 */
SET @v2 = X;
SET @v3 = X;

SELECT s.name
FROM student as s USE INDEX ()
WHERE id BETWEEN @v2 AND @v3;