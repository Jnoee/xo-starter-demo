<div class="page">
	<div class="tree-l-box" layoutH="0">
	    <@sys.organTree rootOrgan />
	    <@dwz.formBar showSubmitBtn=false showCancelBtn=false />
	</div>
	<div id="organBox" class="tree-r-box" layoutH="0"></div>
</div>
<script>
	$(function() {
		setTimeout(function() {
			$("a[organId=${selectedOrganId}]").click();
		}, 10);
	}); 
</script>