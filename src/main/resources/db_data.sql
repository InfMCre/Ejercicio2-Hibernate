
INSERT INTO departments 
	(name, city) 
VALUES 
	('Financiero', 'Bilbao'),
	('Recursos Humanos', 'Bilbao'),
	('Marketing', 'Vitoria'),
	('Comercial', 'Vitoria'),
	('Compras', 'Vitoria'),
	('Logística y Operaciones', 'Bilbao'),
	('Control de Gestión', 'San Sebastián'),
	('Dirección General', 'San Sebastián');

INSERT INTO employees 
	(name, position, salary, boss_id, department_id) 
VALUES 
	('IRULEGI','DIRECTOR',30000, null, 1),
	('ALONSO','PRESIDENTE',45000, 1, 2),
	('MARTINEZ','TRABAJADOR',19800, 1, 3),
	('LOPEZ','TRABAJADOR',17500, 2, 1),
	('ORONOZ','COMERCIAL',22000, 2, 2),
	('LERTXUNDI','COMERCIAL',21900, 3, 1);
