<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Welcome</title>
        
        <!-- Standard reset, fonts and grids -->

        <link rel="stylesheet" type="text/css" href="../css/yui/build/reset-fonts-grids/reset-fonts-grids.css" />
        <link rel="stylesheet" type="text/css" href="../css/yui/build/menu/assets/skins/sam/menu.css" /> 
        <script type="text/javascript" src="../css/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="../css/yui/build/container/container_core.js"></script>
        <script type="text/javascript" src="../css/yui/build/menu/menu.js"></script>

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

        <link rel="stylesheet" type="text/css" href="../css/yui/build/button/assets/skins/sam/button.css"></link>
        <link rel="stylesheet" type="text/css" href="../css/yui/build/container/assets/skins/sam/container.css"></link>
        
        <script type="text/javascript" src="../css/yui/build/element/element-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/button/button-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/connection/connection-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/dragdrop/dragdrop-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/container/container-min.js"></script>

        <link rel="stylesheet" type="text/css" href="../css/yui/build/treeview/assets/skins/sam/treeview.css" />
        <script type="text/javascript" src="../css/yui/build/treeview/treeview-min.js"></script>

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
                    <h1>Welcome</h1>
            </div>
            <div id="bd">
                <div id="yui-main">
                    <div class="yui-b">
                    
                        <div id="topNav" class="yuimenubar yuimenubarnav">
                            <div class="bd">
                                <ul class="first-of-type">
                                    <li class="yuimenubaritem first-of-type">
                                        <a class="yuimenubaritemlabel" href="#stage">Stage</a>
                                        <div id="editStage" class="yuimenu">
                                            <div class="bd">
                                                <ul>
                                                    <li class="yuimenuitem"><a class="yuimenuitemlabel" href="addStage.html?buildPlanId=${buildPlanCommand.id}">Add New Stage</a></li>
                                                    <c:forEach items="${buildPlanCommand.stageCommands}" var="stageCommand">
                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="#">${stageCommand.name}</a>
                                                            <div id="stage${stageCommand.id}" class="yuimenu">
                                                                <div class="bd">
                                                                    <ul>
                                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="editStage.html?stageId=${stageCommand.id}">Edit</a></li>
                                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="deleteStage.html?stageId=${stageCommand.id}">Delete</a></li>
                                                                    </ul>            
                                                                </div>
                                                            </div>                    
                                                        </li>
                                                    </c:forEach>
                                                    
                                                </ul>
                                            </div>
                                        </div>      
                                    </li>
                                    <li class="yuimenubaritem">
                                        <a class="yuimenubaritemlabel" href="#step">Step</a>
                                        <div id="editStageStep" class="yuimenu">
                                            <div class="bd">
                                                <ul>
                                                    <c:forEach items="${buildPlanCommand.stageCommands}" var="stageCommand">
                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="editProject.html?vcs=${vcs}">${stageCommand.name}</a>
                                                            <div class="yuimenu">
                                                                <div class="bd">
                                                                    <ul>
                                                                        <li class="yuimenuitem"><a class="yuimenuitemlabel" href="addStep.html?stageId=${stageCommand.id}">Add New Step</a></li>
                                                                        <c:forEach items="${stageCommand.stepCommands}" var="stepCommand">
                                                                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="#">${stepCommand.command}</a>
                                                                                <div id="step${stepCommand.id}" class="yuimenu">
                                                                                    <div class="bd">
                                                                                        <ul>
                                                                                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="editStep.html?stepId=${stepCommand.id}">Edit</a></li>
                                                                                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="deleteStep.html?stepId=${stepCommand.id}">Delete</a></li>
                                                                                        </ul>            
                                                                                    </div>
                                                                                </div>                    
                                                                            
                                                                            </li>
                                                                        </c:forEach>
                                                                        </li>
                                                                    </ul>            
                                                                </div>
                                                            </div>                    
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>      
                                    </li>
                                </ul>
                            </div>
                         </div>
                    
                    
                    
                    
                    
                    <div id="msg">&nbsp;</div>
                    
                        <div id="markup">
                            <ul>
                                <li>${buildPlanCommand.name}
                                    <ul>
                                        <c:forEach items="${buildPlanCommand.stageCommands}" var="stageCommand">
                                            
                                                <li>${stageCommand.name}
                                                    <ul>
                                                        <c:forEach items="${stageCommand.stepCommands}" var="stepCommand">
                                                                <li>${stepCommand.command}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </li>
                                        
                                        </c:forEach>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        
                        
                        <script type="text/javascript">
                        var tree1;
                        (function() {
                            var treeInit = function() {
                                tree1 = new YAHOO.widget.TreeView("markup");
                                tree1.render();
                                tree1.expandAll();
                                
                                var oContextMenu = new YAHOO.widget.ContextMenu("mytreecontextmenu", {
                                                                                trigger: "markup",
                                                                                lazyload: true, 
                                                                                itemdata: [
                                                                                    { text: "Add"},
                                                                                    { text: "Edit"},
                                                                                    { text: "Delete"}
                                                                                ] });
                                
                                
                                
                                oContextMenu.subscribe("triggerContextMenu", onTriggerContextMenu);                                
                                
                                 
                            };
                            YAHOO.util.Event.onDOMReady(treeInit);
                        })();
                        
                        </script>                        

                    </div>
                </div>
                <div class="yui-b">
                    <%@ include file="/WEB-INF/jsp/leftNav.jsp" %>
                </div>
            </div>
            <div id="ft">
                <p>FOOTER</p>
            </div>
        </div>
        
    </body>
</html>

