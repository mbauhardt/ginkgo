<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>All Build Plans</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- (en) Add your meta data here -->
<!-- (de) Fügen Sie hier Ihre Meta-Daten ein -->

<link href="../css/my_layout.css" rel="stylesheet" type="text/css"/>
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
        <span> 
        <%@ include file="/WEB-INF/jsp/topNav.jsp" %>
        </span> </div>
      <h1> </h1>
    </div>
    <!-- begin: main navigation #nav -->
    <div id="nav">
      <!-- skip anchor: navigation -->
      <a id="navigation" name="navigation"></a>
      <%@ include file="/WEB-INF/jsp/mainNav.jsp" %>   
    </div>
    <!-- end: main navigation -->
    <!-- begin: content area #main -->
    <div id="main">
      <!-- begin: #col1 - first float column -->
      <div id="col1">
        <div id="col1_content" class="clearfix"> </div>
        <%@ include file="/WEB-INF/jsp/user/subNav.jsp" %>
      </div>
      <!-- end: #col1 -->
      <!-- begin: #col2 second float column -->
      <div id="col2">
        <div id="col2_content" class="clearfix"> </div>
        <%@ include file="/WEB-INF/jsp/help.jsp" %>
      </div>
      <!-- end: #col2 -->
      <!-- begin: #col3 static column -->
      <div id="col3">
        <div id="col3_content" class="clearfix">
          <!-- skip anchor: content -->
          <a id="content" name="content"></a> 
          
          <table border="0" cellpadding="0" cellspacing="0" class="full">
                    <caption>
                      Saved Build Plans
                    </caption>
          <thead>
            <tr><th scope="col" colspan="4"><a href="editBuildPlan.html">Add Build Plan</a></th></tr>
          </thead>
          <tbody>
            <tr>
              <th scope="col">Project</th>
              <th scope="col">Build Plan</th>
              <th scope="col">Action</th>
              <th scope="col">Action</th>
            </tr>
            <c:forEach items="${buildPlans}" var="buildPlan">
            <tr>
                <th scope="row" class="sub">${buildPlan.project.name}</th>
                <td><a href="listBuildNumbers.html?buildPlanId=${buildPlan.id}">${buildPlan.name}</a></td>
                <td><a href="editBuildPlan.html?id=${buildPlan.id}">Edit</a></td>
                <td>
                    <form action="deleteBuildPlan.html" method="POST">
                        <input type="hidden" name="id" value="${buildPlan.id}"/>
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </tr>
            </c:forEach>
          </tbody>
        </table>
          
          
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
