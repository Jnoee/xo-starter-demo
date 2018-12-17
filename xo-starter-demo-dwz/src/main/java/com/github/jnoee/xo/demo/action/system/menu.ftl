<div class="accordion" fillSpace="sidebar">
    <div class="accordionHeader">
        <h2 class="fa-sitemap">系统管理</h2>
    </div>
    <div class="accordionContent">
        <ul class="tree expand">
        	<@sec.any name="organ,role,user">
	        	<li>
	        		<span>权限管理</span>
	        		<ul>
	        			<@sec.any name="organ">
	        				<li><@dwz.a href="/system/organ-list">机构管理</@dwz.a></li>
	        			</@sec.any>
	        			<@sec.any name="role">
			            	<li><@dwz.a href="/system/role-list">角色管理</@dwz.a></li>
			            </@sec.any>
			            <@sec.any name="user">
			            	<li><@dwz.a href="/system/user-list">用户管理</@dwz.a></li>
			            </@sec.any>
	        		</ul>
	        	</li>
        	</@sec.any>
        	<@sec.any name="log,index,cache">
	        	<li>
	        		<span>系统维护</span>
		        	<ul>
		        		<@sec.any name="log">
			            	<li><@dwz.a href="/system/log-list">日志管理</@dwz.a></li>
			            </@sec.any>
			            <@sec.any name="index">
			            	<li><@dwz.a href="/system/indexed-entity-list">全文索引</@dwz.a></li>
			            </@sec.any>
			            <@sec.any name="cache">
			            	<li><@dwz.a href="/system/cached-entity-list">实体缓存</@dwz.a></li>
			            </@sec.any>
		            </ul>
	            </li>
            </@sec.any>
        </ul>
    </div>
</div>
