<script>
YAHOO.namespace("example.container");
function init${stageCommandCounter}() {
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

    YAHOO.example.container.editStage${stageCommandCounter} = new YAHOO.widget.Dialog("editStage${stageCommandCounter}", 
                        { width : "30em",
                          fixedcenter : true,
                          visible : false, 
                          constraintoviewport : true,
                          buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
                                  { text:"Cancel", handler:handleCancel } ]
                        });

    YAHOO.example.container.editStage${stageCommandCounter}.callback = { success: handleSuccess,
                         failure: handleFailure };

    YAHOO.example.container.editStage${stageCommandCounter}.render();

    YAHOO.util.Event.addListener("showEditStage${stageCommandCounter}", "click", YAHOO.example.container.editStage${stageCommandCounter}.show, YAHOO.example.container.editStage${stageCommandCounter}, true);
}

YAHOO.util.Event.onDOMReady(init${stageCommandCounter});
</script>

<c:if test="${stageCommandCounter < maxStages}">
<c:set var="maxSteps" value="${fn:length(buildPlanCommand.stageCommands[stageCommandCounter].stepCommands)}" />
<c:set var="maxSteps" value="${maxSteps-1}" />
<dl style="border:dotted">
    <dt>Stage - ${stageCommand.name} &nbsp;&nbsp;&nbsp;
        <span style="font-size:10px">
            <a href="#" id="showEditStage${stageCommandCounter}">
                <span>
                    Edit | 
                </span>
            </a>
        </span>
        <span style="font-size:10px">
            <a href="#" id="showDeleteStage${stageCommandCounter}">
                <span>
                    Delete | 
                </span>
            </a>
        </span>
        <span style="font-size:10px">
            <a href="#" id="showEditStep_${stageCommandCounter}_${maxSteps}">
                <span>
                    Add Step
                </span>
            </a>
        </span>
    </dt>
    <dd>&nbsp;</dd>
    <c:set var="stepCommandCounter" value="-1" />
    <c:forEach items="${buildPlanCommand.stageCommands[stageCommandCounter].stepCommands}" var="stepCommand">
        <c:set var="stepCommandCounter" value="${stepCommandCounter+1}" />
        <%@ include file="/WEB-INF/jsp/user/includeEditStep.jsp" %>
    </c:forEach>
 </dl>
</c:if>


<div id="editStage${stageCommandCounter}">
<form:form method="post" action="editStage.html" modelAttribute="buildPlanCommand">
    <fieldset>
        <legend>Stage</legend>
        <div>
            <form:label path="stageCommands[${stageCommandCounter}].name">Name</form:label>
            <form:input path="stageCommands[${stageCommandCounter}].name" />
        </div>
    </fieldset>
    <form:hidden path="stageCommands[${stageCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="stageIndex" value="${stageCommandCounter}" />
</form:form>
</div>

<!-- DELETE STAGE -->

<script>
YAHOO.namespace("example.container");

function initDelete${stageCommandCounter}() {
var handleYes = function() {
    this.form.submit();
};

var handleNo = function() {
    this.hide();
};

YAHOO.example.container.deleteStage${stageCommandCounter} = 
    new YAHOO.widget.SimpleDialog("deleteStage${stageCommandCounter}", 
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
YAHOO.example.container.deleteStage${stageCommandCounter}.render();
YAHOO.util.Event.addListener("showDeleteStage${stageCommandCounter}", "click", YAHOO.example.container.deleteStage${stageCommandCounter}.show, YAHOO.example.container.deleteStage${stageCommandCounter}, true);
             
}
YAHOO.util.Event.onDOMReady(initDelete${stageCommandCounter});             
</script>

<div id="deleteStage${stageCommandCounter}">
<form:form method="post" action="deleteStage.html" modelAttribute="buildPlanCommand">
    <form:hidden path="stageCommands[${stageCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="stageIndex" value="${stageCommandCounter}" />
</form:form>
</div>

