<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录</title>

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
	<form action="${pageContext.request.contextPath}/login/login.do" method="post">
		<table align="center">
			<tr>
				<th colspan="2" align="center">登 录</th>
			</tr>
			<tr>
				<th><label>用户名</label>：</th>
				<td><input type="text" name="user.userName"></td>
			</tr>
			<tr>
				<th>密<label style="padding-left: 15px">码</label>：</th>
				<td><input type="password" name="user.userPwd">
				</td>
			</tr>
			<tr>
				<th>java.util.Date测试：</th>
				<td><input type="text" name="date">
				</td>
			</tr>
			<tr>
				<th>java.util.List  String测试：</th>
				<td>
					<input type="text" name="list">
					<input type="text" name="list">
					<input type="text" name="list">
					<input type="text" name="list">
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
