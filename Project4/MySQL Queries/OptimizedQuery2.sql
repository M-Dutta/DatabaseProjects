/*
 * List the names of students with id in the range of v2 (id) to v3 (inclusive).
 */
SET @v2 = 10000;
SET @v3 = 90000;

SELECT s.name
FROM student as s
WHERE id BETWEEN @v2 AND @v3;