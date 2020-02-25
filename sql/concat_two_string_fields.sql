UPDATE users SET full_name = CONCAT_FULLNAME FROM (
  SELECT 
  CONCAT(COALESCE(first_name, ''), COALESCE(CONCAT(' ', last_name), ''))
  FROM users
) AS CONCAT_FULLNAME;

UPDATE users SET full_name = SUBSTRING(full_name, 3, LENGTH(full_name))
WHERE LEFT(full_name, 2) = '("';

UPDATE users SET full_name = SUBSTRING(full_name, 1, LENGTH(full_name) - 2)
WHERE RIGHT(full_name, 2) = '")';
