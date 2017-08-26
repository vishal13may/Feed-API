create table user (id int AUTO_INCREMENT, name varchar(50), emailId varchar(50), PRIMARY KEY (id));

create table feed (id int AUTO_INCREMENT, name varchar(50), PRIMARY KEY(id));

create table subscription (id int AUTO_INCREMENT, feedId int, userId int, startDate DATE, endDate DATE,  isActive boolean, PRIMARY KEY(feedId, userId), KEY(id));

create table article (id int AUTO_INCREMENT, title varchar(255), content text, author varchar(50), PRIMARY KEY(id));

create table feed_article (feedId int, articleId int);

ALTER TABLE feed_api.feed_article ADD CONSTRAINT fk_article_id FOREIGN KEY (articleId) REFERENCES feed_api.article(id);

ALTER TABLE feed_api.feed_article ADD CONSTRAINT fk_feed_id FOREIGN KEY (feedId) REFERENCES feed_api.feed(id);

ALTER TABLE feed_api.subscription ADD CONSTRAINT fk_subscription_feed_id FOREIGN KEY (feedId) REFERENCES feed_api.feed(id);

ALTER TABLE feed_api.subscription ADD CONSTRAINT fk_subscription_user_id FOREIGN KEY (userId) REFERENCES feed_api.user(id);


insert into user (name, emailId) values('Vishal', 'vishalos@usc.edu');
insert into user (name, emailId) values('Albert', 'albert@usc.edu');
insert into user (name, emailId) values('Gary', 'gary@usc.edu');

insert into feed (name) values('Technology');
insert into feed (name) values('Arts');
insert into feed (name) values('Economics');
insert into feed (name) values('History');
insert into feed (name) values('Current Affairs');


