<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Example: Website Left Nav With Submenus Built From Markup (YUI Library)</title>
        
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



        <script type="text/javascript">
            YAHOO.util.Event.onContentReady("leftNav", function () {
                var oMenu = new YAHOO.widget.Menu("leftNav", { 
                                                        position: "static", 
                                                        hidedelay:  750, 
                                                        lazyload: true });
                oMenu.render();            
            });
        </script>
        <style type="text/css">
            #leftNav {
                position: static;
            }


            /*
                For IE 6: trigger "haslayout" for the anchor elements in the root Menu by 
                setting the "zoom" property to 1.  This ensures that the selected state of 
                MenuItems doesn't get dropped when the user mouses off of the text node of 
                the anchor element that represents a MenuItem's text label.
            */

            #leftNav .yuimenuitemlabel {
                _zoom: 1;
            }

            #leftNav .yuimenu .yuimenuitemlabel {
                _zoom: normal;
            }

        </style>

    </head>
    <body class="yui-skin-sam" id="yahoo-com">

        <div id="doc" class="yui-t1">
            <div id="hd">
                <!-- start: your content here -->
                    <h1>Example: Website Left Nav With Submenus Built From Markup (YUI Library)</h1>
                <!-- end: your content here -->
            </div>
            <div id="bd">

                <!-- start: primary column from outer template -->
                <div id="yui-main">
                    <div class="yui-b">
                        <c:forEach items="${projects}" var="project">
                            <div id="addBuildPlan_${project.id}">
                            <form:form method="post" action="addBuildPlan.html" modelAttribute="buildPlanCommand" >
                                <fieldset>
                                    <legend>Build Plan</legend>
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
                                <span><a href="#" id="showAddBuildPlan_${project.id}">Add Build Plan</a></span>
                            </span>
                            <hr/>
                                <div class="yui-gd">
                                    <div class="yui-u first">
                                        <c:forEach items="${project.buildPlans}" var="buildPlan">
                                            <p>${buildPlan.name} <a href="buildPlan.html?id=${buildPlan.id}">Edit</a></p>
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

                    <div id="leftNav" class="yuimenu">
                        <div class="bd">
                            <h6 class="first-of-type">Guest</h6>
                            <ul class="first-of-type">
                                <li class="yuimenuitem">
                                    <a class="yuimenuitemlabel" href="welcome.html">
                                        Welcome
                                    </a>
                                </li>
                            </ul>            
                            <h6>User</h6>
                            <ul>
                                <li class="yuimenuitem">
                                    <a class="yuimenuitemlabel" href="../user/welcome.html">
                                        Welcome
                                    </a>
                                </li>
                                <li class="yuimenuitem">
                                    <a class="yuimenuitemlabel" href="../user/projects.html">
                                        Project's
                                    </a>
                                </li>
                                <li class="yuimenuitem">
                                    <a class="yuimenuitemlabel" href="../user/listBuildAgents.html">
                                        Build Agent's
                                    </a>
                                </li>
                            </ul>            
                            <h6>Administration</h6>
                            <ul>
                                <li class="yuimenuitem">
                                    <a class="yuimenuitemlabel" href="../administration/welcome.html">
                                        Welcome
                                    </a>
                                </li>
                                <li class="yuimenuitem">
                                    <a class="yuimenuitemlabel" href="../administration/listUsers.html">
                                        User's
                                    </a>
                                </li>
                            </ul>            

                        </div>
                    </div>                    
                </div>
                <!-- end: secondary column from outer template -->
            </div>
            <div id="ft">

                <p>FOOTER: Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas sit amet metus. Nunc quam elit, posuere nec, auctor in, rhoncus quis, dui. Aliquam erat volutpat. Ut dignissim, massa sit amet dignissim cursus, quam lacus feugiat.</p>

            </div>
        </div>
        
    </body>
</html><!-- presentbright.corp.yahoo.com uncompressed/chunked Thu Feb 19 10:53:17 PST 2009 -->