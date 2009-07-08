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

        <link rel="stylesheet" type="text/css" href="../css/yui/build/container/assets/skins/sam/container.css" />
        <script type="text/javascript" src="../css/yui/build/container/container-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/dragdrop/dragdrop-min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="../css/yui/build/button/assets/skins/sam/button.css" />
        <script type="text/javascript" src="../css/yui/build/connection/connection-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/element/element-min.js"></script>
        <script type="text/javascript" src="../css/yui/build/button/button-min.js"></script>

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
                        <div id="editStep">
                        	<div class="hd">Please enter your information</div>
                        	<div class="bd">
                                <form:form method="post" action="addStage.html" modelAttribute="stageCommand">
                                    <fieldset>
                                        <legend>Stage</legend>
                                        <div class="type-text">
                                            <form:label path="name">Name</form:label>
                                            <form:input path="name" />
                                        </div>
                                    </fieldset>
                                    <form:hidden path="id" />
                                    <form:hidden path="buildPlan" />
                                    <input type="submit" value="save" />
                                </form:form>
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
