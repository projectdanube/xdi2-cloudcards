<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
                
        <script>
            <c:if test="${not empty card}">var card = ${card};</c:if>
            var error = '${errorMsg}';
            var success = '${successMsg}';
        </script>
        
    </head>

    <body ng-controller="ViewCardCtrl">
        <div id="wrapper">

            <div class="card-viewer" ng-style="cardBackground" dynamic-resize>

                <div>
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2 col-md-6 col-md-offset-3 card-content">

                            <div class="alert alert-success" role="alert" ng-show="success">
                                <div class="row">
                                    <div class="col-xs-2 text-center">
                                        <i class="fa fa-check fa-2x"></i>
                                    </div>
                                    <p class="col-xs-10">
                                        {{success}}
                                    </p>
                                </div>
                            </div>
                            <div class="alert alert-danger" role="alert" ng-show="error">
                                <div class="row">
                                    <div class="col-xs-2 text-center">
                                        <i class="fa fa-warning fa-2x"></i>
                                    </div>
                                    <p class="col-xs-10">
                                        {{error}}
                                    </p>
                                </div>
                            </div>

                            <div class="alert alert-info" role="alert" ng-show="inProgress">
                                <div class="row">
                                    <div class="col-xs-2 text-center">
                                        <i class="fa fa-refresh fa-2x fa-spin"></i>
                                    </div>
                                    <p class="col-xs-10">
                                        Loading...
                                    </p>
                                </div>
                            </div>

                        </div>
                    </div>

                </div>

                <div ng-show="card">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2 col-md-6 col-md-offset-3 card-content">
                            <h2>{{card.cloudName + '#' + card.tag || card.name}}</h2>

                            <p><span ng-bind-html="card.description | linky"></span></p>
                            <hr>


                            <div class="form-group" ng-show="card.fields.firstName.value">
                                <label>{{cardFieldLabels['firstName']}}</label>
                                <span ng-show="card.fields.firstName.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.firstName.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.firstName.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.lastName.value">
                                <label>{{cardFieldLabels['lastName']}}</label>
                                <span ng-show="card.fields.lastName.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.lastName.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.lastName.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.nickname.value">
                                <label>{{cardFieldLabels['nickname']}}</label>
                                <span ng-show="card.fields.nickname.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.nickname.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.nickname.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.gender.value">
                                <label>{{cardFieldLabels['gender']}}</label>
                                <span ng-show="card.fields.gender.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.gender.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.gender.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.birthDate.value">
                                <label>{{cardFieldLabels['birthDate']}}</label>
                                <span ng-show="card.fields.birthDate.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.birthDate.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.birthDate.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.nationality.value">
                                <label>{{cardFieldLabels['nationality']}}</label>
                                <span ng-show="card.fields.nationality.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.nationality.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.nationality.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.phone.value">
                                <label>{{cardFieldLabels['phone']}}</label>
                                <span ng-show="card.fields.phone.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.phone.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.phone.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.mobilePhone.value">
                                <label>{{cardFieldLabels['mobilePhone']}}</label>
                                <span ng-show="card.fields.mobilePhone.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.mobilePhone.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.mobilePhone.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.workPhone.value">
                                <label>{{cardFieldLabels['workPhone']}}</label>
                                <span ng-show="card.fields.workPhone.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.workPhone.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.workPhone.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.email.value">
                                <label>{{cardFieldLabels['email']}}</label>
                                <span ng-show="card.fields.email.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.email.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.email.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.website.value">
                                <label>{{cardFieldLabels['website']}}</label>
                                <span ng-show="card.fields.website.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.website.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.website.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.address_street.value">
                                <label>{{cardFieldLabels['address_street']}}</label>
                                <span ng-show="card.fields.address_street.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.address_street.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.address_street.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.address_postalCode.value">
                                <label>{{cardFieldLabels['address_postalCode']}}</label>
                                <span ng-show="card.fields.address_postalCode.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.address_postalCode.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.address_postalCode.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.address_locality.value">
                                <label>{{cardFieldLabels['address_locality']}}</label>
                                <span ng-show="card.fields.address_locality.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.address_locality.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.address_locality.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.address_region.value">
                                <label>{{cardFieldLabels['address_region']}}</label>
                                <span ng-show="card.fields.address_region.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.address_region.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.address_region.value | linky"></span></p>
                            </div>
                            <div class="form-group" ng-show="card.fields.address_country.value">
                                <label>{{cardFieldLabels['address_country']}}</label>
                                <span ng-show="card.fields.address_country.privacy == 'PUBLIC'"><i class="fa fa-globe text-primary" tooltip="Public"></i></span>
                                <span ng-show="card.fields.address_country.privacy == 'PRIVATE'"><i class="fa fa-group text-primary" tooltip="Private"></i></span>
                                <p><span ng-bind-html="card.fields.address_country.value | linky"></span></p>
                            </div>



                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2 col-md-6 col-md-offset-3 card-content">
                            <div class="spacer10"></div>

                            <div class="pull-right">
                                <!-- Connect Button -->
                                <form style="display:inline" action="https://connect-service.projectdanube.org/connect" id="xdiConnect" method="POST">
                                    <input type="hidden" name="xdiMessageEnvelope" value="{{ card.messageConnectButton }}" />
                                    
                                    <!--  a href onclick="$('#xdiConnect').submit();"><img src="assets/images/logo-connect.png" /></a -->
                                    <a href onclick="$('#xdiConnect').submit();" class="btn btn-primary btn-lg" role="button">
	                                    <span class="fa-stack fa-lg">
											<i class="fa fa-cloud fa-stack-2x"></i>
											<i class="fa fa-plug fa-stack-1x text-muted"></i>
										</span>&nbsp;
                                    	XDI Connect
                                    </a>
                                    
                                </form>
                                <div class="spacer10"></div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
    </body>

    </html>