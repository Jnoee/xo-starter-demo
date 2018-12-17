-- 创建系统根机构
insert into Organ (id, parentId, name, status, ordinal, createUserId, createTime, updateUserId, updateTime)
values ('ADMINOID-0000-0000-0000-000000000000', null, '系统根机构', '1', 0, 'ADMINUID-0000-0000-0000-000000000000', NOW(), 'ADMINUID-0000-0000-0000-000000000000', NOW());

-- 创建系统管理员用户
insert into User (id, username, password, name, status, ordinal, defaultActorId, createUserId, createTime, updateUserId, updateTime)
values ('ADMINUID-0000-0000-0000-000000000000', 'admin', 'j6Xdj208bRekjd8a9acwQJL5IbHiws3ncNkgO6Gh9HY=', '系统管理员', '1', 0, 'ADMINAID-0000-0000-0000-000000000000', 'ADMINUID-0000-0000-0000-000000000000', NOW(), 'ADMINUID-0000-0000-0000-000000000000', NOW());

-- 创建系统管理员角色
insert into Role (id, name, privilegs, createUserId, createTime, updateUserId, updateTime)
values('ADMINRID-0000-0000-0000-000000000000', '系统管理员', 'organ:manage,role:manage,user:manage,log:view,index:manage,cache:manage', 'ADMINUID-0000-0000-0000-000000000000', NOW(), 'ADMINUID-0000-0000-0000-000000000000', NOW());

-- 创建系统根机构管理员职务
insert into Actor (id, organId, userId, roleId, name)
values ('ADMINAID-0000-0000-0000-000000000000', 'ADMINOID-0000-0000-0000-000000000000', 'ADMINUID-0000-0000-0000-000000000000', 'ADMINRID-0000-0000-0000-000000000000', '系统管理员');