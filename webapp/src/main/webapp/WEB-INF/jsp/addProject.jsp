<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Your Page Title</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- (en) Add your meta data here -->
<!-- (de) Fügen Sie hier Ihre Meta-Daten ein -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css"/>
<!--[if lte IE 7]>
<link href="patches/patch_layout_draft.css" rel="stylesheet" type="text/css" />
<![endif]-->
</head>
<body>
<div class="page_margins">
  <div class="page">
    <div id="header">
      <div id="topnav">
        <!-- start: skip link navigation -->
        <a class="skip" href="#navigation" title="skip link">Skip to navigation</a><span class="hideme">.</span>
        <a class="skip" href="#content" title="skip link">Skip to content</a><span class="hideme">.</span>
        <!-- end: skip link navigation -->
        <span><a href="#">Login</a> | <a href="#">Contact</a> | <a href="#">Imprint</a></span> </div>
      <h1> CI-Server </h1>
    </div>
    <!-- begin: main navigation #nav -->
    <div id="nav">
      <!-- skip anchor: navigation -->
      <a id="navigation" name="navigation"></a>
      <div class="hlist">
        <ul>
          <li><a href="welcome.html">Home</a></li>
          <li><a href="guest/index.html">Guest</a></li>
          <li><a href="user/index.html">User</a></li>
          <li><a href="administration/index.html">Administration</a></li>
        </ul>
      </div>
    </div>
    <!-- end: main navigation -->
    <!-- begin: content area #main -->
    <div id="main">
      <!-- begin: #col1 - first float column -->
      <div id="col1">
        <div id="col1_content" class="clearfix">
        
         <h6 class="vlist">Available Scm's</h6>
          <ul class="vlist">
            <c:forEach items="${scms}" var="scm">
                <li><a href="${scm.name}">${scm.name}</a></li>
            </c:forEach>
          </ul>
        
         </div>
      </div>
      <!-- end: #col1 -->
      <!-- begin: #col2 second float column -->
      <div id="col2">
        <div id="col2_content" class="clearfix"> 
        
        TODO: Tipps
        
        </div>
      </div>
      <!-- end: #col2 -->
      <!-- begin: #col3 static column -->
      <div id="col3">
        <div id="col3_content" class="clearfix">
          <!-- skip anchor: content -->
          <a id="content" name="content"></a>
        
          <h2>Add Project</h2>
          <form method="post" action="addProject.html" class="yform">
            <fieldset>
              <legend>${scm.name}</legend>
              <div class="type-text">
                  <label for="projectName">Project Name</label>
                  <input type="text" name="projectName" id="projectName" size="20" />
              </div>
              
              <c:set var="size" value="0" />
              <c:forEach items="${scm.parameterNames}" var="scmParameter">
                <div class="type-text">
                    <label for="url">${scmParameter}</label>
                    <input type="text" name="parameter_${size}" id="parameter_${size}" size="20" />
                </div>
                <c:set var="size" value="${size + 1}" />
              </c:forEach>
            </fieldset>
            <input type="hidden" name="size" value="${size}" />
            <input type="hidden" name="scm" value="${scm.name}" />
            <div class="type-button">
              <input type="submit" value="Login" id="submit" name="submit" />
              <input type="reset" />
            </div>
        </form>
          
          
        </div>
        <!-- IE column clearing -->
        <div id="ie_clearing">&nbsp;</div>
      </div>
      <!-- end: #col3 -->
    </div>
    <!-- end: #main -->
    <!-- begin: #footer -->
    <div id="footer">Layout based on <a href="http://www.yaml.de/">YAML</a></div>
    <!-- end: #footer -->
  </div>
</div>
</body>
</html>
