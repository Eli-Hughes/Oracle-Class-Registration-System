Drop table Registered;
Drop table Course;
Drop table Student;

Create table Student
(
	ssn numeric primary key,
	name char(50),
	address varchar(100),
	major	char(10)
);
Create table Course
(
	code char(10) primary key,
	title varchar(50)
);
Create table Registered
(
	ssn numeric,
	code char(10),
	year int,
	semester char(10),
	grade char(1),
	foreign key (ssn) references Student(ssn),
	foreign key (code) references Course(code),
	primary key (ssn,code,year,semester)
);

Insert into Student  values(555550001,'Tom Hanks', '100 Ellen St., Spartanburg, SC 29301', 'CIS');
Insert into Student  values(555550003,'Matt Cutts', '130 Martin St., Spartanburg, SC 29301', 'CS');
Insert into Student  values(555550007,'Mary Jane', '5340 University Way, Spartanburg, SC 29303', 'CS');
Insert into Student  values(555550005,'Courtney Ranes', '1670 Woodwill St., Spartanburg, SC 29301', 'Math');
Insert into Student  values(555550002,'Terry Love', '346 Hill St., Spartanburg, SC 29301', 'CIS');
Insert into Student  values(555550006,'Steve Harry', '7543 Briarwood St., Spartanburg, SC 29304', 'CIS');
Insert into Student  values(555550004,'Will Smith', '7342 Esquire Dr., Spartanburg, SC 29301', 'Math');
Insert into Student  values(555550008,'Caroline Manzo', '246 Altanta Hwy, Spartanburg, SC 29302', 'CS');
Insert into Student  values(555550009,'Jerry Hood', '752 Ellen St., Spartanburg, SC 29301', 'HIS');
Insert into Student  values(555550011,'Underson Hoper', '768 Hood St., Spartanburg, SC 29302', 'CS');



Insert into Course  values('CS200', 'Computer Science I');
Insert into Course  values('CS300', 'Computer Science II');
Insert into Course  values('MATH200', 'Algebra');
Insert into Course  values('MATH100', 'Pre-Calculus');
Insert into Course  values('CS520', 'Database Systems');
Insert into Course  values('CS350', 'Analysis of Algorithms');


Insert into Registered(ssn,code,year,semester)    values(555550001, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550002, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550003, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550004, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550005, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550006, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550007, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550008, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550009, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550011, 'MATH100',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550003, 'CS200',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550008, 'CS200',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550007, 'CS200',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550011, 'CS200',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550003, 'CS520',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550008, 'CS520',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550011, 'CS350',2013, 'Spring');
Insert into Registered(ssn,code,year,semester)    values(555550003, 'CS350',2013, 'Spring');





