drop database if exists whaleWatching;
create database if not exists whaleWatching;

use whaleWatching;

create table customer(
                         cus_Id varchar(35) primary key ,
                         name varchar(155) not null,
                         nic varchar(15) not null,
                         address text not null,
                         e_mail varchar(155) not null,
                         tel varchar(20) not null
);

create table ride(
                     ride_Id varchar(35) primary key ,
                     one_Person_price double not null,
                     typ varchar(20) not null,
                     date date not null ,
                     time time not null,
                     status varchar(25) not null
);


create table payment(
                      pay_Id varchar(35) primary key,
                      date date not null ,
                      amount double not null
);

create table booking(
                        booking_Id varchar(35) primary key ,
                        ride_Id varchar(35) not null,
                        cus_Id varchar(35) not null ,
                        pay_Id varchar(35) not null ,
                        date date not null ,
                        time time not null,
                        constraint foreign key(ride_Id) references ride(ride_Id)
                            on delete cascade on update cascade,
                        constraint foreign key(cus_Id) references customer(cus_Id)
                            on delete cascade on update cascade,
                        constraint foreign key (pay_Id) references payment(pay_Id)
                            on delete cascade on update cascade
);





create table boat(
                     boat_Id varchar(35) primary key ,
                     boat_Name varchar(35) not null ,
                     boat_Type varchar(55) not null,
                     Sheet_Count int(20) not null,
                     description varchar(45) not null
);


create table ride_Boat_Detail(
                                    boat_Id varchar(35) not null ,
                                    ride_Id varchar(35) not null ,
                                    constraint foreign key(boat_Id) references boat(boat_Id)
                                        on delete cascade on update cascade ,
                                    constraint foreign key (ride_Id) references ride(ride_Id)
                                        on delete cascade on update cascade
);

create table employee(
                         emp_Id varchar(35) primary key,
                         name varchar(155) not null,
                         address text not null,
                         tel varchar(20) not null,
                         nic varchar(60) not null,
                         role varchar(105) not null

);

create table employee_Boat_Detail(
                        boat_Id varchar(35) not null ,
                        emp_Id varchar(35) not null ,
                        constraint foreign key (boat_Id) references boat(boat_Id)
                                 on delete cascade on update cascade ,
                        constraint foreign key (emp_Id) references employee(emp_Id)
                                 on delete cascade on update cascade
);

create table salary(
                       sal_Id varchar(35) primary key ,
                       emp_Id varchar(35) not null ,
                       date date not null ,
                       bonus double not null,
                       amount double not null,
                       constraint foreign key (emp_Id) references employee(emp_Id)
                           on delete cascade on update cascade

);

create table attendance(
                           emp_Id varchar(35) not null ,
                           date date not null,
                           in_time time not null,
                           out_time time not null ,
                           constraint foreign key (emp_Id) references employee(emp_Id)
                               on delete cascade on update cascade
);

create table user(
                     userName varchar(40) not null ,
                     password varchar(35) not null
);







