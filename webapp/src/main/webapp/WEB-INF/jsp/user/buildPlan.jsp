<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Your Page Title</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- (en) Add your meta data here -->
<!-- (de) Fügen Sie hier Ihre Meta-Daten ein -->

<link href="../css/my_layout.css" rel="stylesheet" type="text/css"/>

<!-- Combo-handled YUI CSS files: -->
<link rel="stylesheet" type="text/css" href="../css/yui/build/button/assets/skins/sam/button.css"></link>
<link rel="stylesheet" type="text/css" href="../css/yui/build/container/assets/skins/sam/container.css"></link>
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="../css/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="../css/yui/build/element/element-min.js"></script>
<script type="text/javascript" src="../css/yui/build/button/button-min.js"></script>
<script type="text/javascript" src="../css/yui/build/connection/connection-min.js"></script>
<script type="text/javascript" src="../css/yui/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="../css/yui/build/container/container-min.js"></script>

<script>
YAHOO.namespace("example.container");

function initRunBuildPlan() {
var handleYes = function() {
    this.form.submit();
};

var handleNo = function() {
    this.hide();
};

YAHOO.example.container.runBuildPlan = 
    new YAHOO.widget.SimpleDialog("runBuildPlan", 
             { width: "300px",
               fixedcenter: true,
               visible: false,
               draggable: false,
               close: true,
               text: "Do you want to continue?",
               icon: YAHOO.widget.SimpleDialog.ICON_HELP,
               constraintoviewport: true,
               buttons: [ { text:"Yes", handler:handleYes, isDefault:true },
                          { text:"No",  handler:handleNo } ]
             } );
YAHOO.example.container.runBuildPlan.render();
YAHOO.util.Event.addListener("showRunBuildPlan", "click", YAHOO.example.container.runBuildPlan.show, YAHOO.example.container.runBuildPlan, true);
             
}
YAHOO.util.Event.onDOMReady(initRunBuildPlan);             
</script>


<!--[if lte IE 7]>
<link href="patches/patch_layout_draft.css" rel="stylesheet" type="text/css" />
<![endif]-->
</head>
<body class="yui-skin-sam">
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
                &nbsp;    
                <c:set var="maxStages" value="${fn:length(buildPlanCommand.stageCommands)}"/>
                <c:set var="maxStages" value="${maxStages-1}"/>
                <dl class="note">
                <dt>Build Plan - ${buildPlanCommand.name} &nbsp;&nbsp;&nbsp;
                    <span style="font-size:10px">
                        <a href="#">
                            <span>
                                Edit | 
                            </span>
                        </a>
                    </span>
                    <span style="font-size:10px">
                        <a href="#">
                            <span>
                                Delete | 
                            </span>
                        </a>
                    </span>
                    <span style="font-size:10px">
                        <a href="#" id="showEditStage${maxStages}">
                            <span>
                                Add Stage |
                            </span>
                        </a>
                    </span>
                    <span style="font-size:10px">
                        <a href="#" id="showRunBuildPlan">
                            <span>
                                Run Build Plan
                            </span>
                        </a>
                    </span>
                </dt>
                <dd>&nbsp;</dd>
                <c:set var="stageCommandCounter" value="-1" />
                <c:forEach items="${buildPlanCommand.stageCommands}" var="stageCommand">
                    <c:set var="stageCommandCounter" value="${stageCommandCounter+1}" />
                    <%@ include file="/WEB-INF/jsp/user/includeEditStage.jsp" %>
                </c:forEach>
                </dl>
        
                <div id="runBuildPlan">
                    <form:form method="post" action="runBuildPlan.html" cssClass="yform">
                        <input type="hidden" name="id" value="${buildPlanCommand.id}" />
                    </form:form>
                </div>
        
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
