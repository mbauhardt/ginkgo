<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Projects</title>
        
        <!-- Standard reset, fonts and grids -->

        <link rel="stylesheet" type="text/css" href="../css/yui/build/reset-fonts-grids/reset-fonts-grids.css" />
        <link rel="stylesheet" type="text/css" href="../css/yui/build/menu/assets/skins/sam/menu.css" /> 
        <script type="text/javascript" src="../css/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="../css/yui/build/container/container_core.js"></script>
        <script type="text/javascript" src="../css/yui/build/menu/menu.js"></script>


        <link rel="stylesheet" type="text/css" href="../css/yui/build/button/assets/skins/sam/button.css"></link>
        <link rel="stylesheet" type="text/css" href="../css/yui/build/container/assets/skins/sam/container.css"></link>
        <script type="text/javascript" src="../css/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="../css/yui/build/element/element-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/button/button-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/connection/connection-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/dragdrop/dragdrop-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/container/container-min.js"></script>

        <style type="text/css">
            #leftNav {
                position: static;
            }

            #leftNav .yuimenuitemlabel {
                _zoom: 1;
            }

            #leftNav .yuimenu .yuimenuitemlabel {
                _zoom: normal;
            }

        </style>

        <script type="text/javascript">
        YAHOO.util.Event.onContentReady("topNav", function () {
        
            var oMenuBar = new YAHOO.widget.MenuBar("topNav", { 
                                                        autosubmenudisplay: true, 
                                                        hidedelay: 750, 
                                                        lazyload: true });
            oMenuBar.render();
        });
        </script>

    </head>
    <body class="yui-skin-sam" id="yahoo-com">

        <div id="doc" class="yui-t1">
            <div id="hd">
                <!-- start: your content here -->
                    <h1>HEADER</h1>
                <!-- end: your content here -->
            </div>
            <div id="bd">

                <!-- start: primary column from outer template -->
                <div id="yui-main">
                    <div class="yui-b">
                        <div id="topNav" class="yuimenubar yuimenubarnav">
                            <div class="bd">
                                <ul class="first-of-type">
                                    <li class="yuimenubaritem first-of-type">
                                        <a class="yuimenubaritemlabel" href="#projects">Edit Project</a>
                                        <div id="project" class="yuimenu">
                                            <div class="bd">
                                                <ul>
                                                    <li class="yuimenuitem"><a class="yuimenuitemlabel" href="#">New</a>
                                                        <div id="new" class="yuimenu">
                                                            <div class="bd">
                                                                <ul class="first-of-type">
                                                                    <c:forEach items="${vcsList}" var="vcs">
                                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="editProject.html?vcs=${vcs}">${vcs}</a></li>
                                                                    </c:forEach>
                                                                </ul>            
                                                            </div>
                                                        </div>                    
                                                    </li>
                                                    <c:forEach items="${projects}" var="project">
                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="editProject.html?id=${project.id}">${project.name}</a></li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>      
                                    </li>
                                    <li class="yuimenubaritem">
                                        <a class="yuimenubaritemlabel" href="#buildplans">Add Build Plan</a>
                                        <div id="buildplans" class="yuimenu">
                                            <div class="bd">
                                                <c:forEach items="${projects}" var="project">
                                                    <ul>
                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="#" id="showAddBuildPlan_${project.id}">${project.name}</a></li>
                                                    </ul>
                                                </c:forEach>
                                            </div>
                                        </div>      
                                    </li>
                        
                                </ul>            
                            </div>
                         </div>
                        <c:forEach items="${projects}" var="project">
                            <div id="addBuildPlan_${project.id}">
                            <form:form method="post" action="addBuildPlan.html" modelAttribute="buildPlanCommand" >
                                <fieldset>
                                    <legend>Create Build Plan for Project: ${project.name}</legend>
                                    <form:label path="name" >Name</form:label>
                                    <form:input path="name" />
                                </fieldset>
                                <input type="hidden" name="projectId" value="${project.id}" />
                            </form:form>
                            </div>
                            <script>
                            YAHOO.namespace("example.container");
                            function addBuildPlan_${project.id}() {
                                var handleSubmit = function() {
                                    this.submit();
                                };
                                
                                var handleCancel = function() {
                                    this.cancel();
                                };
                                
                                var handleSuccess = function(o) {
                                };
                                
                                var handleFailure = function(o) {
                                };
                            
                                YAHOO.example.container.addBuildPlan_${project.id} = new YAHOO.widget.Dialog("addBuildPlan_${project.id}", 
                                                    { width : "30em",
                                                      fixedcenter : true,
                                                      visible : false, 
                                                      constraintoviewport : true,
                                                      buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
                                                              { text:"Cancel", handler:handleCancel } ]
                                                    });
                            
                                YAHOO.example.container.addBuildPlan_${project.id}.callback = { success: handleSuccess, failure: handleFailure };
                                YAHOO.example.container.addBuildPlan_${project.id}.render();
                                YAHOO.util.Event.addListener("showAddBuildPlan_${project.id}", "click", YAHOO.example.container.addBuildPlan_${project.id}.show, YAHOO.example.container.addBuildPlan_${project.id}, true);
                            }
                            
                            YAHOO.util.Event.onDOMReady(addBuildPlan_${project.id});
                            </script>

                        
                            <span>
                                <b>${project.name}</b>
                            </span>
                            <hr/>
                                <div class="yui-gd">
                                    <div class="yui-u first">
                                        <c:forEach items="${project.buildPlans}" var="buildPlan">
                                            <p>${buildPlan.name} 
                                                <a href="buildPlan.html?id=${buildPlan.id}">Edit</a>
                                                <a href="buildNumbers.html?buildPlanId=${buildPlan.id}">BN</a>
                                             </p>
                                        </c:forEach>
                                    </div>
                                    <div class="yui-u">
                                        <p>
                                            VCS: ${project.vcs}
                                            <c:forEach items="${project.configurationTuples}" var="configurationTuple">
                                                <div>${configurationTuple.tupleKey}: ${configurationTuple.tupleValue}</div>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>
                        </c:forEach>
                    </div>
                </div>
                <!-- end: primary column from outer template -->

                <!-- start: secondary column from outer template -->
                <div class="yui-b">
                    <%@ include file="/WEB-INF/jsp/leftNav.jsp" %>
                </div>
                <!-- end: secondary column from outer template -->
            </div>
            <div id="ft">

                <p>FOOTER</p>

            </div>
        </div>
        
    </body>
</html>
