CREATE TABLE file (
	id bigserial,
	name text,
	objectid oid
);

INSERT INTO file(name, objectid)
VALUES('test image', lo_import('/tmp/neko.jpg'));

SELECT lo_export(objectid, '/tmp/neko_exp.jpg')
FROM file
WHERE name = 'test image';