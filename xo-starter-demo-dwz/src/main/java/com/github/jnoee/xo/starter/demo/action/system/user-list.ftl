<div class="page">
    <div class="pageHeader">
        <@dwz.pageForm action="/system/user-list" />
    </div>
    <div class="pageContent">
        <div class="panelBar">
            <ul class="toolBar">
                <li><@dwz.a href="/system/user-add" target="dialog" title="新增用户"><span class="fa-user-plus">新增</span></@dwz.a></li>
            </ul>
        </div>
        <table class="table" width="100%" layoutH="112">
            <thead>
                <tr>
                    <th width="100px">用户名</th>
                    <th width="100px">姓名</th>
                    <th>默认职务</th>
					<th width="130px">创建时间</th>
					<th width="100px">创建人</th>
					<th width="130px">修改时间</th>
					<th width="100px">修改人</th>
                    <th width="80px" align="center">状态</th>
                    <th width="180px">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list userPage.contents as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.name}</td>
                    <td>${user.defaultActor.fullName}</td>
					<td>${user.createTime?datetime}</td>
					<td>${user.createUser.name}</td>
					<td>${user.updateTime?datetime}</td>
					<td>${user.updateUser.name}</td>
                    <td class="${user.status.color}">${user.status}</td>
                    <td>
                        <@dwz.a href="/system/user-edit?user=${user.id}" target="dialog" height="SS" title="编辑用户">编辑</@dwz.a>
                        <#if user.status == EnabledStatus.ENABLED>
                            <@dwz.a href="/system/user-disable?user=${user.id}" target="ajaxTodo" title="您确定要停用该用户吗？">停用</@dwz.a>
                        <#else>
                            <@dwz.a href="/system/user-enable?user=${user.id}" target="ajaxTodo" title="您确定要启用该用户吗？">启用</@dwz.a>
                        </#if>
                        <@dwz.a href="/system/user-pwd-reset?user=${user.id}" target="dialog" width="SS" height="SSS">重置密码</@dwz.a>
                        <@dwz.a href="/system/actor-list?user=${user.id}" target="dialog" width="S" height="SS">职务管理</@dwz.a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
        <div class="panelBar">
            <@dwz.pageNav pageModel=userPage />
        </div>
    </div>
</div>