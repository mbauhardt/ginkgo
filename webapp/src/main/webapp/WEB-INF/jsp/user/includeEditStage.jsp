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

<dt>Stage - ${stageCommand.name} &nbsp;&nbsp;&nbsp;
    <span style="font-size:10px">
        <a href="#" id="showEditStage${stageCommandCounter}">
            <span>
                Edit | 
            </span>
        </a>
    </span>
    <span style="font-size:10px">
        <a href="#">
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


<div id="editStage${stageCommandCounter}">
<form:form method="post" action="editStage.html" modelAttribute="buildPlanCommand" cssClass="yform">
    <fieldset>
        <legend>Stage</legend>
        <div class="type-text">
            <label for="name">Name</label>
            <form:input path="stageCommands[${stageCommandCounter}].name" />
        </div>
    </fieldset>
    <div class="type-button">
        <input type="submit" value="Update" id="submit" name="submit" />
        <input type="reset" />
    </div>
    <form:hidden path="stageCommands[${stageCommandCounter}].id" />
    <form:hidden path="id" />
    <input type="hidden" name="listIndex" value="${stageCommandCounter}" />
</form:form>
</div>
