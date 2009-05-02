<script>
YAHOO.namespace("example.container");
function init() {
    var handleSubmit = function() {
        this.submit();
    };
    
    var handleCancel = function() {
        this.cancel();
    };
    
    var handleSuccess = function(o) {
        var response = o.responseText;
        response = response.split("<!")[0];
        document.getElementById("errorEditStage${stageCommandCounter}").innerHTML = response;
    };
    
    var handleFailure = function(o) {
        alert("Submission failed: ");
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

YAHOO.util.Event.onDOMReady(init);
</script>

<c:if test="${stageCommandCounter < maxStages}"> 
<dl class="important">
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
            <a href="#">
                <span>
                    New Step
                </span>
            </a>
        </span>
    </dt>
    <dd>&nbsp;</dd>
    <c:forEach items="${buildPlanCommand.stageCommands[stageCommandCounter].stepCommands}" var="stepCommand">
    <dl class="info">
        <dt>Step - ${stepCommand.name} &nbsp;&nbsp;&nbsp;
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
                        Delete 
                    </span>
                </a>
            </span>
        </dt>
        <dd>${stepCommand.command}</dd>
    </dl>
    </c:forEach>
 </dl>
</c:if>


<div id="editStage${stageCommandCounter}">
<form:form method="post" action="editStage.html" modelAttribute="buildPlanCommand" cssClass="yform">
    <fieldset>
        <legend>Stage</legend>
        <div class="type-text">
            <label for="name">Name</label>
            <form:input path="stageCommands[${stageCommandCounter}].name" />
        </div>
    </fieldset>
    <form:hidden path="stageCommands[${stageCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="listIndex" value="${stageCommandCounter}" />
</form:form>
</div>

<!-- DELETE STAGE -->

<script>
YAHOO.namespace("example.container");

function initDelete() {
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
YAHOO.util.Event.onDOMReady(initDelete);             
</script>

<div id="deleteStage${stageCommandCounter}">
<form:form method="post" action="deleteStage.html" modelAttribute="buildPlanCommand" cssClass="yform">
    <form:hidden path="stageCommands[${stageCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="listIndex" value="${stageCommandCounter}" />
</form:form>
</div>

