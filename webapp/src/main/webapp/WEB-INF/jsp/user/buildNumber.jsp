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

        <link rel="stylesheet" type="text/css" href="../css/yui/build/datatable/assets/skins/sam/datatable.css" />
        <script type="text/javascript" src="../css/yui/build/element/element-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/datasource/datasource-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/datatable/datatable-min.js"></script>

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
                    <h1>Welcome</h1>
                <!-- end: your content here -->
            </div>
            <div id="bd">

                <!-- start: primary column from outer template -->
                <div id="yui-main">
                    <div class="yui-b">
                        <div id="markup">
                            <table id="buildNumber">
                                <thead>
                                    <tr>
                                        <th>Command</th>
                                        <th>Build Agent</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${buildCommands}" var="buildCommand">
                                        <tr>
                                            <td>${buildCommand.command}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>    
                                            <c:forEach items="${buildCommand.buildAgentStatus}" var="status">
                                                <td>&nbsp;</td>
                                                <td>${status.key}</td>
                                                <td>${status.value}</td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        
                        <script type="text/javascript">
                            YAHOO.util.Event.addListener(window, "load", function() {
                                YAHOO.example.EnhanceFromMarkup = new function() {
                                    var myColumnDefs = [
                                        {key:"buildCommand",label:"Build Command"},
                                        {key:"buildAgent",label:"Build Agent"},
                                        {key:"buildStatus",label:"Build Status"},
                                    ];
                            
                                    this.myDataSource = new YAHOO.util.DataSource(YAHOO.util.Dom.get("buildNumber"));
                                    this.myDataSource.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
                                    this.myDataSource.responseSchema = {
                                        fields: [{key:"buildCommand"},
                                                {key:"buildAgent"},
                                                {key:"buildStatus"},
                                        ]
                                    };
                            
                                    this.myDataTable = new YAHOO.widget.DataTable("markup", myColumnDefs, this.myDataSource,
                                            {caption:"Build Number: ${buildNumber.buildNumber}"}
                                    );
                                };
                            });
                        </script>                                            
                    
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
