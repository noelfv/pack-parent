<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<div class="ui-accordion ui-widget ui-helper-reset" style="padding-bottom: 10px;">
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
				Usuario no autorizado
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>