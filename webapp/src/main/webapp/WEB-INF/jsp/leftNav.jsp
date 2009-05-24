
<script type="text/javascript">
    YAHOO.util.Event.onContentReady("leftNav", function () {
    var oMenu = new YAHOO.widget.Menu("leftNav", {
    position: "static",
    hidedelay: 750,
    lazyload: true });
    oMenu.render();
    });
 </script>

<div id="leftNav" class="yuimenu">
    <div class="bd">
        <h6 class="first-of-type">Guest</h6>
        <ul class="first-of-type">
            <li class="yuimenuitem">
                <a class="yuimenuitemlabel" href="<%=request.getContextPath() %>/guest/welcome.html">
                    Welcome
                                    </a>
            </li>
        </ul>
        <h6>User</h6>
        <ul>
            <li class="yuimenuitem">
                <a class="yuimenuitemlabel" href="<%=request.getContextPath() %>/user/welcome.html">
                    Welcome
                                    </a>
            </li>
            <li class="yuimenuitem">
                <a class="yuimenuitemlabel" href="<%=request.getContextPath() %>/user/projects.html">
                    Project's
                                    </a>
            </li>
            <li class="yuimenuitem">
                <a class="yuimenuitemlabel" href="<%=request.getContextPath() %>/user/buildAgents.html">
                    Build Agent's
                                    </a>
            </li>
        </ul>
        <h6>Administration</h6>
        <ul>
            <li class="yuimenuitem">
                <a class="yuimenuitemlabel" href="<%=request.getContextPath() %>/administration/welcome.html">
                    Welcome
                                    </a>
            </li>
            <li class="yuimenuitem">
                <a class="yuimenuitemlabel" href="<%=request.getContextPath() %>/administration/users.html">
                    User's
                                    </a>
            </li>
        </ul>
    </div>
</div>                    
