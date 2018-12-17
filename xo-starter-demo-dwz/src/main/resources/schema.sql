/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/13 10:19:24                           */
/*==============================================================*/


/*==============================================================*/
/* Table: Organ                                                 */
/*==============================================================*/
create table Organ
(
   id                   char(36) not null,
   parentId             char(36),
   name                 varchar(60) not null,
   status               varchar(3) not null comment '0.停用 1.启用',
   ordinal              int not null,
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
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
   id                   char(36) not null,
   name                 varchar(20) not null,
   username             varchar(20) not null,
   password             varchar(120) not null,
   status               varchar(3) not null comment '0.停用 1.启用',
   ordinal              int not null,
   defaultActorId       char(36),
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: Role                                                  */
/*==============================================================*/
create table Role
(
   id                   char(36) not null,
   name                 varchar(60) not null,
   privilegs            varchar(2000),
   createUserId         char(36) not null,
   createTime           datetime not null,
   updateUserId         char(36) not null,
   updateTime           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: Actor                                                 */
/*==============================================================*/
create table Actor
(
   id                   char(36) not null,
   organId              char(36) not null,
   userId               char(36) not null,
   roleId               char(36) not null,
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
   id                   char(36) not null,
   operator             varchar(20) not null,
   operateTime          datetime not null,
   message              varchar(800) not null,
   entityId             char(36),
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

