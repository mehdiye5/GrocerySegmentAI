<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head> 

	<%@ page import="java.util.ArrayList" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Instacart UI">
	<title>Create Data Object</title>
	<script type="text/javascript" src="jQuery/jquery-3.1.1.min.js"></script>
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="assets/css/normalize.css">
	<link rel="stylesheet" href="assets/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/css/fontello.css">
	<link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
	<link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
	<link href="assets/css/animate.css" rel="stylesheet" media="screen">
	<link rel="stylesheet" href="assets/css/bootstrap-select.min.css"> 
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/icheck.min_all.css">
	<link rel="stylesheet" href="assets/css/price-range.css">
	<link rel="stylesheet" href="assets/css/owl.carousel.css">  
	<link rel="stylesheet" href="assets/css/owl.theme.css">
	<link rel="stylesheet" href="assets/css/owl.transitions.css">
	<link rel="stylesheet" href="assets/css/style.css">
	<link rel="stylesheet" href="assets/css/responsive.css">
	
	<script type="text/javascript" src="jQuery/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <link rel="stylesheet" href="//malihu.github.io/custom-scrollbar/jquery.mCustomScrollbar.min.css">
	
	<link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/sidebar-themes.css">
    <link rel="shortcut icon" type="image/png" href="img/favicon.png" /> </head>
</head>
<body>
	<div class="page-wrapper toggled light-theme">
        <nav id="sidebar" class="sidebar-wrapper">
            <div class="sidebar-content">
                <!-- sidebar-brand  -->
                <div class="sidebar-item sidebar-brand text-white font-weight-bold"><i class="fa fa-database"></i><h3 style="padding-left:10px">GrocerySegmentAI Service</h3></div>
                <!-- sidebar-header  -->
                <!-- sidebar-search  -->
                <div class="sidebar-item sidebar-search">
                    <div>
                        <div class="input-group">
                            <input type="text" class="form-control search-menu" placeholder="Search...">
                            <div class="input-group-append"> <span class="input-group-text">
                                    <i class="fa fa-search" aria-hidden="true"></i>
                                </span> </div>
                        </div>
                    </div>
                </div>
                <!-- sidebar-menu  -->
                <div class=" sidebar-item sidebar-menu">
                    <ul>
                        <li class="header-menu"> <span>Funtions</span> </li>
                        <li class="sidebar-dropdown">
                            <a href="#"> <i class="fa fa-database"></i> <span class="menu-text">Getting Started</span> <span class="badge badge-pill badge-primary">2</span> </a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li> <a href="CreateDataObjectPage">Step 1: Create Data Objects </a> </li>
                                    <!-- <li> <a href="#">Step 2: Set Up Data Object Relations</a> </li> -->
                                    <li> <a href="VIewSegmentationPage">Step 2: View the Segmentation</a> </li>
                                </ul>
                            </div>
                        </li>
                        <li>
                            <a href="#"> <i class="fa fa-book"></i> <span class="menu-text">Basic Usage</span> </a>
                        </li>
                        <li>
                            <a href="#"> <i class="fa fa-calendar"></i> <span class="menu-text">Customizing</span> </a>
                        </li>
                        <li>
                            <a href="#"> <i class="fa fa-folder"></i> <span class="menu-text">Troubleshooting</span> </a>
                        </li>
                    </ul>
                </div>
                <!-- sidebar-menu  -->
            </div>
            <!-- sidebar-footer  -->
            <div class="sidebar-footer">
                <div class="dropdown">
                    <a href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fa fa-bell"></i> <span class="badge badge-pill badge-primary notification">1</span> </a>
                    <div class="dropdown-menu notifications" aria-labelledby="dropdownMenuMessage">
                        <div class="notifications-header"> <i class="fa fa-bell"></i> Notifications </div>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">
                            <div class="notification-content">
                                <div class="icon"> <i class="fas fa-check text-success border border-success"></i> </div>
                                <div class="content">
                                    <div class="notification-detail">Download latest update </div>
                                    <div class="notification-time"> 20 minutes ago </div>
                                </div>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div> <a class="dropdown-item text-center" href="#">All notifications</a> </div>
                </div>
                <div>
                    <a id="pin-sidebar" href="#"> <i class="fa fa-power-off"></i> </a>
                </div>
                <div class="pinned-footer">
                    <a href="#"> <i class="fas fa-ellipsis-h"></i> </a>
                </div>
            </div>
        </nav>
        <!-- page-content  -->
        <main class="page-content">
            <div id="overlay" class="overlay"></div>
            <div class="container-fluid">
                <div class="row d-flex align-items-center p-3 border-bottom">
                    <div class="col-md-1">
                        <a id="toggle-sidebar" class="btn rounded-0 p-3" href="#"> <i class="fas fa-bars"></i> </a>
                    </div>
                    <div class="col-md-8">
                        <nav aria-label="breadcrumb" class="align-items-center">
                            <a href="index.html" class="breadcrumb-back" title="Back"></a>
                            <ol class="breadcrumb d-none d-lg-inline-flex m-0">
                                <li class="breadcrumb-item"><a href="/Instacart">Home</a></li>
                                <li class="breadcrumb-item active" aria-current="page">GrocerySegmentAI Instruction Page</li>
                            </ol>
                        </nav>
                    </div>
                    <div class="col-md-3 text-left"> <a href="https://sharebootstrap.com/docu-free-bootstrap-4-documentation-theme/" class="btn btn-sm btn-primary btn-rounded">Free Download</a> </div>
                </div>
                <div class="row p-lg-4">
                    <article class="main-content col-md-9 pr-lg-5">
                        <!-- title -Add bootstrap helpers for spacing -->
                        <h1 class="mb-2 mt-3">Create GrocerySegmentAI Features</h1>
                        <!--//title -->
                        <p class="lead mb-3"> How to get started with GrocerySegmentAI </p>
                        <p>This service was brought to you by the DiversiT team.</p>             
                        
                        <p>In this page you will need to map the GrocerySegmentAI Features. These features are intended to provide us with the necessary features to yield high performing ML model. First begin by telling use which column from the <strong>Orders Object</strong> that you created in the previous step that signifies User ID (a unique indentifier for each user). Similarly with the <strong>Order ID</strong>. <strong>Timestamp</strong> is the column that is supposed to contain the time and the date of the order. Next we need to provide <strong>Product ID</strong> from the Products Object created previously. Additional features column is asking to provide additional column names or features that you think might improve the prediction accuracy of the ML model. Similar concept applied to Order Product table</p>
                        
                        <!-- Sep -->
                        <hr class="my-5">
                        <!-- alert -->
                        
                        
                        <div class="col-md-6">
				                    <div class="box-for overflow">
				                        <div class="col-md-12 col-xs-12 register-blocks">
				                            <h2>Set GrocerySegmentAI Features: </h2> 
				                            <form action="CreateInstacart" method="GET">
				                                <div class="form-group">
				                                    <label for="name">Orders Object User ID Column Name</label>
				                                    <input type="text" class="form-control" id="orders_user_id" name="orders_user_id">
				                                </div>
				                                <div class="form-group">
				                                    <label for="name">Orders Object Order ID Column Name</label>
				                                    <input type="text" class="form-control" id="orders_order_id" name="orders_order_id">
				                                </div>
				                                <div class="form-group">
				                                    <label for="password">Orders Object Timestamp Column Name</label>
				                                    <input type="text" class="form-control" id="orders_timestamp" name="orders_timestamp">
				                                </div>
				                                
				                                
				                                <hr class="my-5">
				                                
				                                
				                                <div class="form-group">
				                                    <label for="email">Product Object Product ID Column Name</label>
				                                    <input type="text" class="form-control" id="product_product_id" name="product_product_id">
				                                </div>
				                                <div class="form-group">
				                                	<div id="product_group">
					                                    <label for="email" style="display:block">Product Object Additional Features</label>
					                                    <div style="display: flex">
					                                    <input type="text" class="form-control" id="product_additional_features" name="product_additional_features">
					                                    <span class="input-group-btn" style="width:auto">
															<button type="button" class="addProduct btn btn-default" style="width:auto; height:auto">
																<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
															</button>	
														</span>
														</div>
													</div>
				                                </div>
				                                
				                                <hr class="my-5">
				                                
				                                <div class="form-group">
				                                    <label for="password">Order Product Object Order ID Column Name</label>
				                                    <input type="text" class="form-control" id="product_orders_order_id" name="product_orders_order_id">
				                                </div>
				                                <div class="form-group">
				                                    <label for="email">Order Product Object Product ID Column Name</label>
				                                    <input type="text" class="form-control" id="product_orders_product_id" name="product_orders_product_id">
				                                </div>
				                                
				                                <div class="form-group">
				                                	<div id="order_product_group">
					                                    <label for="email" style="display:block">Order Product Object Additional Features</label>
					                                    <div style="display: flex">
					                                    <input type="text" class="form-control" id="order_product_additional_features" name="order_product_additional_features">
					                                    <span class="input-group-btn" style="width:auto">
															<button type="button" class="addOrderProduct btn btn-default" style="width:auto; height:auto">
																<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
															</button>	
														</span>
														</div>
													</div>
				                                </div>
				                               <input type="hidden" id="custId" name="instacart_product_id" value="${instacart_product_id}">
				                               <input type="hidden" id="custId" name="instacart_orders_id" value="${instacart_orders_id}">
				                               <input type="hidden" id="custId" name="instacart_pruduct_orders_id" value="${instacart_pruduct_orders_id}">
				                                <div class="text-center">
				                                    <button type="submit" class="btn btn-default">Create GrocerySegmentAI Object</button>
				                                </div>
				                            </form>
				                        </div>
				                    </div>
				                </div>
				                
				                
                        <!-- 
                        <div class="register-area" style="background-color: rgb(249, 249, 249);">
				            <div class="container">
				
				                
				              
				              </div>
				        </div>
                         -->
                     
                     </article>
                    <aside class="col-md-3 d-none d-md-block border-left">
                        <div class="widget widget-support-forum p-md-3 p-sm-2"> <span class="icon icon-forum"></span>
                            <h4>Looking for help? Join Community</h4>
                            <p>Couldnt find what your are looking for ? Why not join out support forums and let us help you.</p> <a href="#" class="btn btn-light">Support Forum</a> </div>
                        <hr class="my-5">
                        <ul class="aside-nav nav flex-column">
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-1">Typography</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-3">File Tree</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-4">Table</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-5">Accordion</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-6">Video</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-7">Code</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-8">Alert</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-9">List</a> </li>
                            <li class="nav-item"> <a data-scroll="" class="nav-link text-dark" href="#section-10">Carousel</a> </li>
                        </ul>
                    </aside>
                </div>
            </div>
        </main>
        <!-- page-content" -->
    </div>
    <!-- page-wrapper -->
    <!-- using online scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous">
    </script>
    <script src="//malihu.github.io/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="js/prism.min.js"></script>
    <!-- using local scripts -->
    <!-- <script src="../node_modules/jquery/dist/jquery.min.js"></script>
    <script src="../node_modules/popper.js/dist/umd/popper.min.js"></script>
    <script src="../node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="../node_modules/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script> -->
    <script src="js/main.js"></script>
    
    <script>
    	const log = console.log
    	let instacart_product_id = ${instacart_product_id}
    	let instacart_orders_id = ${instacart_orders_id}
    	let instacart_pruduct_orders_id = ${instacart_pruduct_orders_id}
    	
    	log("Product id is:")
    	log(instacart_product_id)
    	log(instacart_orders_id)
    	log(instacart_pruduct_orders_id)
    
    	let numProducts = 0;
	    $(document).on('click', '.addProduct', function(){	    	
	    	let html = '<label for="email" style="display:block">Product Object Additional Features</label><div style="display: flex"><input type="text" class="form-control" id="product_additional_features" name="product_additional_features"><span class="input-group-btn" style="width:auto"><button type="button" class="addProduct btn btn-default" style="width:auto; height:auto"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button></span></div>'
	    	$('#product_group').append(html);
			
		});
	    
	    $(document).on('click', '.addOrderProduct', function(){	    	
	    	let html = '<label for="email" style="display:block">Order Product Object Additional Features</label><div style="display: flex"><input type="text" class="form-control" id="order_product_additional_features" name="order_product_additional_features"><span class="input-group-btn" style="width:auto"><button type="button" class="addOrderProduct btn btn-default" style="width:auto; height:auto"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button></span></div>'
	    	$('#order_product_group').append(html);
			
		});
    
    </script>
</body>
</html>