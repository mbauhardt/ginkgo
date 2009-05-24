<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="css/yui/build/reset-fonts-grids/reset-fonts-grids.css" /> 
</head>

<body>
<div id="doc">					
	<div id="hd">
		<p>Header</p>
	</div>
	<div id="bd">
		<p>
            <form method="post" action="j_security_check">
                <fieldset>
                  <legend>Login Required</legend>
                    <label>Email</label>
                    <input type="text" name="j_username"/>
                    <label>Password</label>
                    <input type="password" name="j_password" />
                </fieldset>
                  <input type="submit" value="Login"/>
            </form>
        </p>
	</div>
	<div id="ft">
		<p>Footer</p>
	</div>
</div>
</body>
</html>
