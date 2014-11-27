<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	    
	<title>Acceso LDAP</title>
</head>
<body>
	<s:form action="loginAction">			
		<s:hidden name="desa" value="desa"></s:hidden>
		<s:textfield name="codldap" value="P017239"></s:textfield>
		<s:textfield name="password"></s:textfield>
		<s:submit value="Aceptar" button="true" cssStyle="float: left;"></s:submit>	
	</s:form>
</body>
</html>