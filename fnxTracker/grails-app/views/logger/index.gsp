<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Fnx Tracker</title>
	</head>
	<body>
	
		<g:form class="form-horizontal" action="log">
  			<div class="control-group">
    			<label class="control-label" for="inputEmail">Fecha</label>
    			<div class="controls">
    				<input type="hidden" name="date" value="${new Date().format("dd/MM/yyyy")}"/>
					<div class="btn-group">
			  			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
			  				${new Date().format("dd/MM/yyyy")}
			    			<span class="caret"></span>
			  			</a>
			  			<ul class="dropdown-menu">
			    			<g:each in="${days}">
								<li>${it.format("dd/MM/yyyy")}</li>
							</g:each>
			  			</ul>
					</div>
    			</div>
  			</div>
			<div class="control-group">
    			<label class="control-label" for="hours">Cant Horas</label>
    			<div class="controls">
    				<input type="text" name="hours" id="hours"/>
    			</div>
  			</div>
			<div class="control-group">
    			<label class="control-label" for="task">Tarea</label>
    			<div class="controls">
    				<input type="text" name="task" id="task"/>
    			</div>
  			</div>
  			
  			<div class="form-actions">
  				<button type="submit" class="btn btn-primary">Cargar</button>
  				<button type="button" class="btn">Cancelar</button>
			</div>
		</g:form>
	
		
	</body>
</html>
