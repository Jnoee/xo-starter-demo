<div class="page">
    <div class="pageContent">
    	<div class="panelBar">
            <ul class="toolBar">
                <li>
                    <@dwz.a href="/system/cached-entity-evict" target="selectedTodo" rel="cachedEntityClasses" title="您确定要清理选中的实体缓存吗？">
						<span class="fa-minus">清理实体缓存</span>
					</@dwz.a>
                </li>
                <li class="line"></li>
                <li>
                    <@dwz.a href="/system/entity-regions-evict" target="ajaxTodo" title="您确定要清理所有实体缓存吗？">
						<span class="fa-minus-square">清理所有实体缓存</span>
					</@dwz.a>
                </li>
                <li>
                    <@dwz.a href="/system/collection-regions-evict" target="ajaxTodo" title="您确定要清理所有集合缓存吗？">
						<span class="fa-minus-square-o">清理所有集合缓存</span>
					</@dwz.a>
                </li>
                <li>
                    <@dwz.a href="/system/query-regions-evict" target="ajaxTodo" title="您确定要清理所有查询缓存吗？">
						<span class="fa-search-minus">清理所有查询缓存</span>
					</@dwz.a>
                </li>
                <li class="line"></li>
                <li>
                    <@dwz.a href="/system/all-regions-evict" target="ajaxTodo" title="您确定要清理所有缓存吗？">
						<span class="fa-trash">清理所有缓存</span>
					</@dwz.a>
                </li>
            </ul>
        </div>
        <table class="table" width="100%" layoutH="75">
            <thead>
                <tr>
                    <th width="80" align="center">
                        <@s.checkbox class="checkboxCtrl" group="cachedEntityClasses" value="true" />
                    </th>
                    <th>实体类名</th>
                </tr>
            </thead>
            <tbody>
                <#list cachedEntityClasses as cachedEntityClass>
	                <tr>
	                    <td>
	                        <@s.checkbox name="cachedEntityClasses" value=cachedEntityClass.name />
	                    </td>
	                    <td>${cachedEntityClass.name}</td>
	                </tr>
                </#list>
            </tbody>
        </table>
		<div class="panelBar"></div>
    </div>
</div>