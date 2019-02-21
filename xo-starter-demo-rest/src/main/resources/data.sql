-- 创建系统根机构
insert into Organ (id, parentId, name, status, ordinal, createUserId, createTime, updateUserId, updateTime)
values (100000000000000000, null, '系统根机构', '1', 0, 100000000000000001, NOW(), 100000000000000001, NOW());

-- 创建系统管理员用户
insert into User (id, username, password, name, status, ordinal, defaultActorId, createUserId, createTime, updateUserId, updateTime)
values (100000000000000001, 'admin', 'j6Xdj208bRekjd8a9acwQJL5IbHiws3ncNkgO6Gh9HY=', '系统管理员', '1', 0, 100000000000000003, 100000000000000001, NOW(), 100000000000000001, NOW());

-- 创建系统管理员角色
insert into Role (id, name, privilegs, createUserId, createTime, updateUserId, updateTime)
values(100000000000000002, '系统管理员', 'organ:manage,role:manage,user:manage,log:view,index:manage,cache:manage', 100000000000000001, NOW(), 100000000000000001, NOW());

-- 创建系统根机构管理员职务
insert into Actor (id, organId, userId, roleId, name)
values (100000000000000003, 100000000000000000, 100000000000000001, 100000000000000002, '系统管理员');