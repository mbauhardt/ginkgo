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
                                        <a class="yuimenubaritemlabel" href="editProject.html?projectId=${project.id}">Edit</a>  
                                    </li>
                                    <li class="yuimenubaritem first-of-type">
                                        <a class="yuimenubaritemlabel" href="deleteProject.html?projectId=${project.id}">Delete</a>  
                                    </li>
                                    <li class="yuimenubaritem first-of-type">
                                        <a class="yuimenubaritemlabel" href="addBuildPlan.html?projectId=${project.id}">Add Build Plan</a>  
                                    </li>
                                </ul>            
                            </div>
                         </div>


                        <b>${project.name}</b>
                         <div class="yui-gd">
                            <div class="yui-u first">
                                <c:forEach items="${project.buildPlans}" var="buildPlan">
                                    <p><a href="buildPlan.html?buildPlanId=${buildPlan.id}">${buildPlan.name}</a></p>
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
