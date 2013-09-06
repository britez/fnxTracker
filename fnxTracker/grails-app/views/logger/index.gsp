<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Fnx Tracker</title>
	</head>
	<body>
	
	    <div class="page-header">
    		<h1>Cargar horas <small>ingrese la cantidad de horas para la tarea que quieras.</small></h1>
    	</div>
    	
    	<h6>Progreso de horas: ${percent}%</h6>
		<div class="progress progress-striped active">
  			<div class="bar" style="width: ${percent}%;"></div>
		</div>
    	
		<g:if test="${flash.message}">
		  	<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<strong>OK!</strong>
				${flash.message}
			</div>
	  	</g:if>
    	
    	<div class="container-fluid">
  			<div class="row-fluid">
    			<div class="span5">
    				<h6>Formulario de carga de horas</h6>		
					<g:form class="form-horizontal" action="log">
					
			  			<div class="control-group">
			  			
			    			<label class="control-label" for="inputEmail">Fecha</label>
			    			
			   				<input type="hidden" name="date" id="date" value="${new Date().format("dd/MM/yyyy")}"/>
			   				
			    			<div class="controls">
								<div class="btn-group">
						  			<a class="btn dropdown-toggle" id="dateSelect" data-toggle="dropdown" href="#">
						  				${new Date().format("dd/MM/yyyy")}
						    			<span class="caret"></span>
						  			</a>
						  			<ul class="dropdown-menu">
						    			<g:each in="${days}">
											<li><a href="#" class="selectable">${it.format("dd/MM/yyyy")}</a></li>
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
    			
    			</div>
    			
    			<div class="span5">
    				<h6>Horas cargadas</h6>		
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
    			</div>
  			</div>
		</div>
    	
		<script type="text/javascript">
			$(document).ready(function() {
				$(".selectable").click(function(){
					var date = $(this).html();
					$("#date").val(date);
					$("#dateSelect").html(date + "<span class='caret'></span>");
				});
			});
		</script>
	
		
	</body>
</html>
