<div class="page">
	<div class="tree-l-box" layoutH="0">
	    <ul class="tree" layoutH="36">
	        <li>
	            <span>角色列表</span>
	            <@dwz.a href="/system/role-add" target="dialog" class="right">[新增角色]</@dwz.a>
	            <ul>
	                <#list roles as role>
	                	<li><@dwz.a href="/system/role-edit?role=${role.id}" target="ajax" rel="roleBox" roleId="${role.id}">${role.name}</@dwz.a></li>
	                </#list>
	            </ul>
	        </li>
	    </ul>
	    <@dwz.formBar showSubmitBtn=false showCancelBtn=false />
	</div>
	<div id="roleBox" class="tree-r-box" layoutH="0"></div>
</div>
<script>
    $(function() {
        setTimeout(function() {
            $("a[roleId=${selectedRoleId}]").click();
        }, 10);
    }); 
</script>