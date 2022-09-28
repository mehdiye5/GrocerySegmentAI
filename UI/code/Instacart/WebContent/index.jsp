<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 

	<%@ page import="java.util.ArrayList" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Instacart UI">
	<title>GrocerySegmentAI UI</title>
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
                                <li class="breadcrumb-item active" aria-current="page">GrocerySegmentAIInstruction Page</li>
                            </ol>
                        </nav>
                    </div>
                    <div class="col-md-3 text-left"> <a href="https://sharebootstrap.com/docu-free-bootstrap-4-documentation-theme/" class="btn btn-sm btn-primary btn-rounded">Free Download</a> </div>
                </div>
                <div class="row p-lg-4">
                    <article class="main-content col-md-9 pr-lg-5">
                        <!-- title -Add bootstrap helpers for spacing -->
                        <h1 class="mb-2 mt-3">Welcome to the GrocerySegmentAI Service </h1>
                        <!--//title -->
                        <p class="lead mb-3"> How to get started with GrocerySegmentAI </p>
                        <p>This service was brought to you by the DiversiT team.</p>
                        <div class="alert alert-primary d-flex justify-content-start align-items-center" role="alert"> <span class="fa fa-info mr-4"></span>
                            <p class="m-0"><strong>Important*</strong> Please note that this site was designed for demonstartion purposes only and is not inteded to have a full range of functionality</p>
                        </div>
                        <p> This guide will help you get started with using the GrocerySegmentAI! All the important steps such as uploading your data for analysis, file structures, table relations and data analysis are included in this documented, but should you have any questions, always feel free to reach out to diversit@fakeemail.com </p>
                        <!-- Sep -->
                        <hr class="my-5">
                        <!-- alert -->
                        <div class="alert alert-primary d-flex justify-content-start align-items-center" role="alert"> <span class="fa fa-info mr-4"></span>
                            <p class="m-0"> This documentation is always evolving. If you've not been here for a while, perhaps check out our cominity section for frequently asked questions. This documentation is always evolving. This is a primary alert with <a href="#" class="alert-link">help section</a>. Give it a click if you like. </p>
                        </div>
                        <!-- heading -->
                        <h3 class="font-weight-bold mb-2">
                            Get Started
                        </h3>
                        <p> To get started using GrocerySegmentAI click on the "Get Started" panel on the left and do the following: </p>
                        <ol>
                            <li><strong>Create Data Objects</strong> upload your data files to GrocerySegmentAI by providing AWS S3 link with the access key</li>
                            <li><strong>Set Up Data Object Relations</strong>, once data obejects were created, in this section primary and foreign keys need to linked</li>
                            <li><strong>View Data Object Segmentation</strong>, once data obejects were created, in this section primary and foreign keys need to linked</li>
                            <!--  
                            <li><strong class="badge badge-danger-soft"><code>npm install gulp-cli -g</code></strong>: If you don't have the Gulp command line interface, you need to install it.</li>
                            <li><strong class="badge badge-danger-soft"><code>npm install</code></strong>: Open your command line to the root directory of your unzipped theme and run to install all of Landkit's dependencies.</li>
                            -->
                        </ol>
                        <!-- Sep -->
                        
                        <%-- 
                        <hr class="my-5">
                        <h3 class="font-weight-bold mb-2">
                            Code example
                        </h3>
                        <p>The Prism source, highlighted with Prism (dont you just love how meta this is?):</p>
                        <p>The <a href="https://www.w3.org/TR/html5/grouping-content.html#the-pre-element">recommended way to mark up a code block</a> (both for semantics and for Prism) is a <code class=" language-markup"><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>pre</span><span class="token punctuation">&gt;</span></span></code> element with a <code class=" language-markup"><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>code</span><span class="token punctuation">&gt;</span></span></code> element inside, like so:</p>
                        <figure class="highlight"><pre><code class="language-html" data-lang="html"><span class="c">&lt;!-- As a link --&gt;</span>
<span class="nt">&lt;nav</span> <span class="na">class=</span><span class="s">"navbar navbar-light bg-light"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;a</span> <span class="na">class=</span><span class="s">"navbar-brand"</span> <span class="na">href=</span><span class="s">"#"</span><span class="nt">&gt;</span>Navbar<span class="nt">&lt;/a&gt;</span>
<span class="nt">&lt;/nav&gt;</span>

<span class="c">&lt;!-- As a heading --&gt;</span>
<span class="nt">&lt;nav</span> <span class="na">class=</span><span class="s">"navbar navbar-light bg-light"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;span</span> <span class="na">class=</span><span class="s">"navbar-brand mb-0 h1"</span><span class="nt">&gt;</span>Navbar<span class="nt">&lt;/span&gt;</span>
<span class="nt">&lt;/nav&gt;</span></code></pre></figure>
                        <hr class="my-5">
                        <figure class="highlight"><pre><code class="language-html" data-lang="html"><span class="nt">&lt;nav</span> <span class="na">class=</span><span class="s">"navbar navbar-expand-lg navbar-light bg-light"</span><span class="nt">&gt;</span>
  <span class="nt">&lt;div</span> <span class="na">class=</span><span class="s">"container"</span><span class="nt">&gt;</span>
    <span class="nt">&lt;a</span> <span class="na">class=</span><span class="s">"navbar-brand"</span> <span class="na">href=</span><span class="s">"#"</span><span class="nt">&gt;</span>Navbar<span class="nt">&lt;/a&gt;</span>
  <span class="nt">&lt;/div&gt;</span>
<span class="nt">&lt;/nav&gt;</span></code></pre></figure>                    
                    
                     --%>
                     
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
</body>
</html>