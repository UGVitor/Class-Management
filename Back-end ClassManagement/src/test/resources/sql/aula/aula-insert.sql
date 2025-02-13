insert into LOGIN (id, login, password, perfil_id) values (105, 'Silva', '123456', 1);
insert into DISCIPLINA (id, nome, id_login) values (105, 'Sistema', 105);
insert into DISCIPLINA (id, nome, id_login) values (106, 'Português', 105);
insert into HORARIOS (id, hora_inicio, hora_termino) values (105, '15:15:00', '15:50:00');
insert into SALA (id, capacidade, numero, id_tiposala) values (105, 35, 8, 1);
INSERT INTO TURMA (id, nome, id_curso, id_periodo, id_semestre, id_turno) VALUES (105, 'ADS2M', 1, 4, 2, 1);

insert into AULA (id, id_dia_semana, id_disciplina, id_horario, id_aula, id_turma) values (100, 1, 105, 105, 105, 105);
