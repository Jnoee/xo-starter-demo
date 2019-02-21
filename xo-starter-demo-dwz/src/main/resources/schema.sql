/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/2/21 14:56:43                           */
/*==============================================================*/


/*==============================================================*/
/* Table: Organ                                                 */
/*==============================================================*/
create table Organ
(
   id                   bigint not null,
   parentId             bigint,
   name                 varchar(60) not null,
   status               varchar(3) not null comment '0.停用 1.启用',
   ordinal              int not null,
   createUserId         bigint not null,
   createTime           datetime not null,
   updateUserId         bigint not null,
   updateTime           datetime not null,
   primary key (id),
   constraint FK_Organ_parentId foreign key (parentId)
      references Organ (id) on delete cascade on update restrict
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   id                   bigint not null,
   name                 varchar(20) not null,
   username             varchar(20) not null,
   password             varchar(120) not null,
   status               varchar(3) not null comment '0.停用 1.启用',
   ordinal              int not null,
   defaultActorId       bigint,
   createUserId         bigint not null,
   createTime           datetime not null,
   updateUserId         bigint not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: Role                                                  */
/*==============================================================*/
create table Role
(
   id                   bigint not null,
   name                 varchar(60) not null,
   privilegs            varchar(2000),
   createUserId         bigint not null,
   createTime           datetime not null,
   updateUserId         bigint not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: Actor                                                 */
/*==============================================================*/
create table Actor
(
   id                   bigint not null,
   organId              bigint not null,
   userId               bigint not null,
   roleId               bigint not null,
   name                 varchar(60) not null,
   primary key (id),
   constraint FK_Actor_organId foreign key (organId)
      references Organ (id) on delete cascade on update restrict,
   constraint FK_Actor_userId foreign key (userId)
      references User (id) on delete cascade on update restrict,
   constraint FK_Actor_roleId foreign key (roleId)
      references Role (id) on delete cascade on update restrict
);

/*==============================================================*/
/* Table: BizLog                                                */
/*==============================================================*/
create table BizLog
(
   id                   bigint not null,
   operator             varchar(20) not null,
   operateTime          datetime not null,
   message              varchar(800) not null,
   entityId             varchar(60),
   origData             varchar(2000),
   newData              varchar(2000),
   primary key (id)
);

/*==============================================================*/
/* Index: IDX_User_username                                     */
/*==============================================================*/
create unique index IDX_User_username on User
(
   username
);

