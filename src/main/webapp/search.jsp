<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en" ng-app="CloudCardApp">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Cloud Card</title>

        <link rel="shortcut icon" href="<c:url value="/assets/images/favicon.ico"/>" />

        <link href="<c:url value="/assets/libs/bootstrap/3.2.0/css/bootstrap.min.css"/>" rel="stylesheet">
        <link href="<c:url value="/assets/libs/font-awesome/4.2.0/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/assets/css/cloud.css"/>" rel="stylesheet">

        <script src="<c:url value="/assets/libs/jquery/1.11.1/jquery-1.11.1.min.js"/>"></script>
        <script src="<c:url value="/assets/libs/angularjs/1.3.0/angular.min.js"/>"></script>
        <script src="<c:url value="/assets/libs/angularjs/1.3.0/angular-sanitize.min.js"/>"></script>
        <script src="<c:url value="/assets/libs/angular-bootstrap/0.11.2/ui-bootstrap-tpls-0.11.2.min.js"/>"></script>

        <script src="<c:url value="/app/app.js"/>"></script>

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<c:url value="/assets/libs/html5shiv/3.7.2/html5shiv.min.js"/>"></script>
            <script src="<c:url value="/assets/libs/respond.js/1.4.2/respond.min.js"/>"></script>
        <![endif]-->

    </head>

    <body ng-controller="SearchCardCtrl">
        <div id="wrapper">

            <div class="card-viewer" ng-style="cardBackground" dynamic-resize>

                <div>
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3 card-content">
                            <h2>Cloud Cards</h2>

                            <form role="form" ng-submit="searchCard()">
                            	<div class="row">
	                            	<div class="col-md-4 col-md-offset-4">
	                                	<input type="text" class="form-control" placeholder="=cloudCard" ng-model="cloudCard" name="cloudCard" autofocus>
	                                </div>
                                </div>
                                
                                <div class="row">
                            		<div class="col-md-4 col-md-offset-4">
                            
		                                <div class="radio">
		                                    <label>
		                                        <input type="radio" ng-model="env" id="envProd" value="PROD" checked>PROD
		                                    </label>
		                                </div>
		                                <div class="radio">
		                                    <label>
		                                        <input type="radio" ng-model="env" id="envOte" value="OTE">OTE
		                                    </label>
		                                </div>
	                                
	                                </div>
                                </div>
                                <br />
                                
                                <div class="col-md-3 col-md-offset-9">
                                	<button class="btn btn-lg btn-primary btn-block" type="button" ng-click="searchCard()">Search</button>
                                </div>
                                
                            </form>
                        </div>
                        
                    </div>

                </div>

            </div>

        </div>
    </body>

    </html>