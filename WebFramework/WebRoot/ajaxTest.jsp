<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'MyJsp.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
input[type=text] {
	width: 140px;
	border-width: 1px;
	border-style: solid;
}

input[type=password] {
	width: 140px;
	border-width: 1px;
	border-style: solid;
}
</style>

</head>

<body>
	<form action="login.do" method="post">
		<table align="center">
			<tr>
				<th colspan="2" align="center">登 录</th>
			</tr>
			<tr>
				<th><label>用户名</label>：</th>
				<td><input type="text" name="userName"></td>
			</tr>
			<tr>
				<th>密<label style="padding-left: 15px">码</label>：</th>
				<td><input type="password" name="userPwd">
				</td>
			</tr>
			<tr>
				<td><input type="submit" name="btnSubimt" value="Login">
				</td>
				<td><input type="reset" name="btnCancel" value="Cancel">
				</td>
			</tr>
		</table>
		<div align="center">
			${msg }
		</div>
	</form>
</body>
</html>
