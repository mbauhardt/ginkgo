<script>
YAHOO.namespace("example.container");
function initEditStep_${stageCommandCounter}_${stepCommandCounter}() {
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

    YAHOO.example.container.editStep_${stageCommandCounter}_${stepCommandCounter} = new YAHOO.widget.Dialog("editStep_${stageCommandCounter}_${stepCommandCounter}", 
                        { width : "30em",
                          fixedcenter : true,
                          visible : false, 
                          constraintoviewport : true,
                          buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
                                  { text:"Cancel", handler:handleCancel } ]
                        });

    YAHOO.example.container.editStep_${stageCommandCounter}_${stepCommandCounter}.callback = { success: handleSuccess,
                         failure: handleFailure };

    YAHOO.example.container.editStep_${stageCommandCounter}_${stepCommandCounter}.render();

    YAHOO.util.Event.addListener("showEditStep_${stageCommandCounter}_${stepCommandCounter}", "click", YAHOO.example.container.editStep_${stageCommandCounter}_${stepCommandCounter}.show, YAHOO.example.container.editStep_${stageCommandCounter}_${stepCommandCounter}, true);
}

YAHOO.util.Event.onDOMReady(initEditStep_${stageCommandCounter}_${stepCommandCounter});
</script>

<c:if test="${stepCommandCounter < maxSteps}">
<dl style="border:dotted">
    <dt>Step
        - ${stepCommand.name} &nbsp;&nbsp;&nbsp;
        <span style="font-size:10px">
            <a href="#" id="showEditStep_${stageCommandCounter}_${stepCommandCounter}">
                <span>
                    Edit | 
                    </span>
            </a>
        </span>
        <span style="font-size:10px">
            <a href="#" id="showDeleteStep_${stageCommandCounter}_${stepCommandCounter}">
                <span>
                    Delete 
                    </span>
            </a>
        </span>
    </dt>
    <dd>${stepCommand.command}</dd>
</dl>
</c:if>


<div id="editStep_${stageCommandCounter}_${stepCommandCounter}">
<form:form method="post" action="editStep.html" modelAttribute="buildPlanCommand">
    <fieldset>
        <legend>Step</legend>
        <div>
            <form:label path="stageCommands[${stageCommandCounter}].stepCommands[${stepCommandCounter}].name">Name</form:label>
            <form:input path="stageCommands[${stageCommandCounter}].stepCommands[${stepCommandCounter}].name" />
        </div>
        <div class="type-text">
            <form:label path="stageCommands[${stageCommandCounter}].stepCommands[${stepCommandCounter}].command">Command</form:label>
            <form:input path="stageCommands[${stageCommandCounter}].stepCommands[${stepCommandCounter}].command" />
        </div>
    </fieldset>
    <form:hidden path="stageCommands[${stageCommandCounter}].stepCommands[${stepCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="stageIndex" value="${stageCommandCounter}" />
    <input type="hidden" name="stepIndex" value="${stepCommandCounter}" />
</form:form>
</div>

<!-- DELETE STEP -->

<script>
YAHOO.namespace("example.container");

function initDeleteStep_${stageCommandCounter}_${stepCommandCounter}() {
var handleYes = function() {
    this.form.submit();
};

var handleNo = function() {
    this.hide();
};

YAHOO.example.container.deleteStep_${stageCommandCounter}_${stepCommandCounter} = 
    new YAHOO.widget.SimpleDialog("deleteStep_${stageCommandCounter}_${stepCommandCounter}", 
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
YAHOO.example.container.deleteStep_${stageCommandCounter}_${stepCommandCounter}.render();
YAHOO.util.Event.addListener("showDeleteStep_${stageCommandCounter}_${stepCommandCounter}", "click", YAHOO.example.container.deleteStep_${stageCommandCounter}_${stepCommandCounter}.show, YAHOO.example.container.deleteStep_${stageCommandCounter}_${stepCommandCounter}, true);
             
}
YAHOO.util.Event.onDOMReady(initDeleteStep_${stageCommandCounter}_${stepCommandCounter});             
</script>

<div id="deleteStep_${stageCommandCounter}_${stepCommandCounter}">
<form:form method="post" action="deleteStep.html" modelAttribute="buildPlanCommand">
    <form:hidden path="stageCommands[${stageCommandCounter}].stepCommands[${stepCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="stageIndex" value="${stageCommandCounter}" />
    <input type="hidden" name="stepIndex" value="${stepCommandCounter}" />
</form:form>
</div>

