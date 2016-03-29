<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.main.Bean"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Internal Details</title>
</head>
<body>

	<table align="center">
		<tr>
			<th>Hello World Logs</th>
			<th>All Logs</th>
		</tr>
		<tr valign="top">
			<td>
				<table border="1">
					<tr>
						<th>IP Address</th>
						<th>Time Stamp</th>
					</tr>
					<%
						Bean bean = new Bean();

						Map<String, String> map = bean.mapPut();
						if (map.size() == 0) {
					%><tr>
						<td colspan="2">
							<%
								out.print("No records");
							%>
						</td>
					</tr>
					<%
						} else {
							for (Map.Entry<String, String> entry : map.entrySet()) {
					%><tr>
						<td>
							<%
								out.print(entry.getValue());
							%>
						</td>
						<td>
							<%
								out.print(entry.getKey());
							%>
						</td>
					</tr>
					<%
						}
						}
					%>
				</table>
			</td>
			<td>
				<table border="1">
					<tr>
						<th>IP Address</th>
						<th>Time Stamp</th>
					</tr>
					<%
						Map<String, String> map1 = bean.mapPutAll();
						if (map1.size() == 0) {
					%><tr>
						<td colspan="2">
							<%
								out.print("No records");
							%>
						</td>
					</tr>
					<%
						} else {
							for (Map.Entry<String, String> entry : map1.entrySet()) {
					%><tr>
						<td>
							<%
								out.print(entry.getValue());
							%>
						</td>
						<td>
							<%
								out.print(entry.getKey());
							%>
						</td>
					</tr>
					<%
						}
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<th>Hello World Minute Aggregate</th>
			<th>All end-point Minute Aggregate</th>
		</tr>
		<tr valign="top">
			<td>


				<table border="1">
					<tr>
						<th>Time Stamp</th>
						<th>Count</th>
					</tr>
					<%
						Map<String, Integer> map3 = bean.getResultsHW();
						if (map3.size() == 0) {
					%><tr>
						<td colspan="2">
							<%
								out.print("No records");
							%>
						</td>
					</tr>
					<%
						} else {
							for (Map.Entry<String, Integer> entry : map3.entrySet()) {
					%><tr>
						<td>
							<%
								out.print(entry.getKey());
							%>
						</td>
						<td>
							<%
								out.print(entry.getValue());
							%>
						</td>
					</tr>
					<%
						}
						}
					%>
				</table>



			</td>
			<td>


				<table border="1">
					<tr>
						<th>Time Stamp</th>
						<th>Count</th>
					</tr>
					<%
						Map<String, Integer> map2 = bean.getResults();
						if (map.size() == 0) {
					%><tr>
						<td colspan="2">
							<%
								out.print("No records");
							%>
						</td>
					</tr>
					<%
						} else {
							for (Map.Entry<String, Integer> entry : map2.entrySet()) {
					%><tr>
						<td>
							<%
								out.print(entry.getKey());
							%>
						</td>
						<td>
							<%
								out.print(entry.getValue());
							%>
						</td>
					</tr>
					<%
						}
						}
					%>
				</table>

			</td>
		</tr>
	</table>
</body>
</html>