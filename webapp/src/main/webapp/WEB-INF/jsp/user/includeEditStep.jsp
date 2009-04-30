
<form:form method="post" action="editStep.html" modelAttribute="buildPlan.stages[0].steps[0]" cssClass="yform">
    <fieldset>
        <legend>Step</legend>
        <div class="type-text">
            <label for="name">Name</label>
            <form:input path="name" />
        </div>
        <div class="type-text">
            <label for="name">Command</label>
            <form:input path="command" />
        </div>
    </fieldset>
    <div class="type-button">
        <input type="submit" value="Update" id="submit" name="submit" />
        <input type="reset" />
    </div>
    <form:hidden path="id" />
</form:form>
