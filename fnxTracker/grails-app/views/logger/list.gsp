<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Fnx Tracker</title>
	</head>
	<body>
	
	    <div class="page-header">
    		<h1>Horas cargadas <small>listado de horas cargadas.</small></h1>
    	</div>
    	
    	<table class="table table-bordered">
    		<thead>
    			<tr>
    				<th>Fecha</th>
    				<th>Tarea</th>
    				<th>Horas</th>
    			</tr>
    		</thead>
    		
    		<tbody>
    			<g:each in="${logs}">
    				<tr>
    					<td>${it.date}</td>
    					<td>${it.task}</td>
    					<td>${it.hours}</td>
    				</tr>
				</g:each>
    		</tbody>
		</table>
	</body>
</html>
