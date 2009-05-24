<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
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

    </head>
    <body class="yui-skin-sam" id="yahoo-com">
        <div id="doc" class="yui-t1">
            <div id="hd">
                    <h1>Edit Project</h1>
            </div>
            <div id="bd">
                <div id="yui-main">
                    <div class="yui-b">
                      <form:form method="post" action="editProject.html"  modelAttribute="projectCommand" cssClass="yform">
                        <fieldset>
                          <legend>Project</legend>
                          <div>
                            <form:label path="name">Name</form:label>
                            <form:input path="name"/>
                          </div>
                          <div>
                            <form:label path="vcs">VCS</form:label>
                            <form:select path="vcs" onchange="window.location.href='editProject.html?id=${projectCommand.id}&vcs='+options[selectedIndex].value"> 
                                <form:option value="-" label="--Please Select"/> 
                                <form:options items="${vcsList}" /> 
                            </form:select> 
                          </div>
                          <div>
                            <c:set var="counter" value="0" />
                            <c:forEach items="${projectCommand.configurationTupleCommands}" var="tuple">
                                <div>
                                    <form:label path="configurationTupleCommands[${counter}].tupleValue">${tuple.tupleKey}</form:label>
                                    <form:input path="configurationTupleCommands[${counter}].tupleValue"/>
                                </div>
                                <c:set var="counter" value="${counter+1}" />
                            </c:forEach>
                          </div>
                        </fieldset>
                        <div>
                          <input type="submit" value="Update" />
                        </div>
                        <form:hidden path="id"/>
                      </form:form>
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
