connect 'jdbc:rmi://localhost:1124/jdbc:cloudscape:musclog;create=true'
;

drop table        musclogusers;
drop table        bandsmusicians;
drop table        show;


create table musclogusers (
   username  varchar(15)  NOT NULL PRIMARY KEY,
   password  varchar(15)  NOT NULL
) 
;

create table bandsmusicians (
   username     varchar(25)       NOT NULL,
   bandname     varchar(25)       NOT NULL,
   bandbio      varchar(1000), 
   yearformed   int
)
;

create table show(
   username          varchar(25)      NOT NULL,
   bandname          varchar(25)      NOT NULL,
   showyear          int              NOT NULL,
   showmonth         int,
   showday           int,              
   setlist           varchar(300),
   journal           varchar(5000),
   bandmembers       varchar(100),
   showvenue         varchar(30),
   showcity          varchar(30)      NOT NULL,
   showstate         varchar(30)      NOT NULL
)
;
