/*Creating database*/
create database if not exists oop_assignment1;

/*Creating table: user*/
use oop_assignment1;
create table if not exists user (
	userID integer not null primary key auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    firstName varchar(255),
    lastName varchar(255),
    contactNumber varchar(20),
    email varchar(255),
    about text,
    userEditFlag boolean default false
);
/*Adding default data to table: user*/
use oop_assignment1;
insert into user (username, password, firstName, lastName, contactNumber, email, about, userEditFlag)
values ('admin', 'admin', 'Nhuan', 'Huynh', '000', 'trying.huynh159@gmail.com', 'Fuck you all!', false);

/*Creating table category*/
use oop_assignment1;
create table if not exists category (
	categoryID integer not null primary key auto_increment,
    categoryName text not null,
    categoryEditFlag boolean default false
);
/*Adding default data to table: category*/
use oop_assignment1;
insert into category (categoryName, categoryEditFlag)
values ('Science', false);
insert into category (categoryName, categoryEditFlag)
values ('IT', false);
insert into category (categoryName, categoryEditFlag)
values ('Travel', false);

/*Creating table post*/
use oop_assignment1;
create table if not exists post (
	postID integer not null primary key auto_increment,
    postTitle text not null,
    postIntroduction text not null,
    postContent text not null,
    postCategoryID integer not null references category(categoryID),
    postDate datetime not null default current_timestamp(),
    postReadingCount integer not null default 0,
    postEditFlag boolean default false
);
/*Adding default data to table: post*/
use oop_assignment1;
insert into post (postTitle, postIntroduction, postContent, postCategoryID, postDate, postEditFlag)
values ('Title 1', 'Introduction 1', 'Content 1', 1, current_timestamp(), false);
insert into post (postTitle, postIntroduction, postContent, postCategoryID, postDate, postEditFlag)
values ('Title 2', 'Introduction 2', 'Content 2', 2, current_timestamp(), false);
insert into post (postTitle, postIntroduction, postContent, postCategoryID, postDate, postEditFlag)
values ('Title 3', 'Introduction 3', 'Content 3', 3, current_timestamp(), false);

/*Creating table comment*/
use oop_assignment1;
create table if not exists comment (
	commentID integer not null primary key auto_increment,
    postID integer not null references post(postID),
    commentContent text not null,
    commentUserName varchar(255) not null default 'Unknown',
    commentDate datetime not null default current_timestamp()
);
/*Adding default values to table: comment*/
use oop_assignment1;
insert into comment (postID, commentUserName, commentContent, commentDate)
values (1, 'User1', 'Comment 1', current_timestamp());
insert into comment (postID, commentUserName, commentContent, commentDate)
values (2, 'User2', 'Comment 2', current_timestamp());
insert into comment (postID, commentUserName, commentContent, commentDate)
values (3, 'User3', 'Comment 3', current_timestamp());


