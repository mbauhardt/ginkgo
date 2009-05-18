<script type="text/javascript">
YAHOO.util.Event.onContentReady("topNav", function () {

    var oMenuBar = new YAHOO.widget.MenuBar("topNav", { 
                                                autosubmenudisplay: true, 
                                                hidedelay: 750, 
                                                lazyload: true });
    oMenuBar.render();
});
</script>

<div id="topNav" class="yuimenubar yuimenubarnav">
    <div class="bd">
        <ul class="first-of-type">
            <li class="yuimenubaritem first-of-type">
                <a class="yuimenubaritemlabel" href="#plugins">Plugins</a>
                <div id="communication" class="yuimenu">
                    <div class="bd">
                        <ul>
                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="http://360.yahoo.com">SVN</a></li>
                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="http://alerts.yahoo.com">GIT</a></li>
                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="http://avatars.yahoo.com">Avatars</a></li>
                        </ul>
                    </div>
                </div>      
            </li>
            <li class="yuimenubaritem">
                <a class="yuimenubaritemlabel" href="#projects">Create Project</a>
                <div id="project" class="yuimenu">
                    <div class="bd">
                        <ul>
                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="http://360.yahoo.com">SVN</a></li>
                            <li class="yuimenuitem"><a class="yuimenuitemlabel" href="http://alerts.yahoo.com">GIT</a></li>
                        </ul>
                    </div>
                </div>      
            </li>

        </ul>            
    </div>
 </div>