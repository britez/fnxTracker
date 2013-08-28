<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Fnx Tracker</title>
	</head>
	<body>
		<div class="row">
			<g:each in="${days}">
				${it.format("dd/MM/yyyy")}
			</g:each>
		</div>
	</body>
</html>
