<html><head>
      <meta http-equiv="content-type" content="text/html; charset=utf-8">
      <title>Connect JavaScript - jQuery Login Example</title>
      
      <style type="text/css">
      
      .fbButton{ background-color: #F6F6F6; background-image: url('${pageContext.servletContext.contextPath}/img/facebook_icon.png');
			   background-position: left; background-repeat: no-repeat; background-size: 28px 31px; border-color: #DDDDDD #CCCCCC #CCCCCC #DDDDDD;
			   border-radius: 2px 2px 2px 2px;border-style: solid; border-width: 1px;color: #333333; cursor: pointer; font-family: Arial, sans-serif ; 
			   font-size: 15px;font-style:normal; font-weight: bold; opacity: 1;padding-left: 27px;text-shadow: 1px 1px white; width:190px; 
			   line-height: 31px; height:31px; float:none;
			   
			  }
      </style>
  </head>
  <body>
      <h1>Connect JavaScript - jQuery Login Example</h1>
      <div>
          <button id="login" class="fbButton">Login</button>
          <button id="logout">Logout</button>
          <button id="disconnect">Disconnect</button>
      </div>
      <div id="user-info" >hello</div>
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js">
    </script>
  <div id="fb-root"></div>
  </body>
  </html>
  <script src="http://connect.facebook.net/en_US/all.js"></script>
  <script>
      // initialize the library with the API key
      FB.init({
          apiKey: '779705588786457'
      });


      // fetch the status on load
      FB.getLoginStatus(handleSessionResponse);

      $('#login').bind('click', function () {
    	  FB.login(function(response) {
	   		   if (response.authResponse) {
	   			 var accessToken = response.authResponse.accessToken;
	   		     FB.api('/me', function(response) {
	   		    	var userid = response.id;
	   	          	var fname = response.first_name;
	   	          	var lname = response.last_name;
	   	          	console.log(accessToken+" "+userid+" "+fname+" "+lname);
	   			});
	   		} else {
	   			console.log('User cancelled login or did not fully authorize.');
	   		}
	   	}, {scope: 'user_education_history, user_location, user_notes, user_work_history, user_website, user_status, friends_education_history, friends_location, friends_notes, friends_work_history, friends_website, friends_status, offline_access, email'}
	   	);
      });


      $('#logout').bind('click', function () {
          FB.logout(handleSessionResponse);
      });

      $('#disconnect').bind('click', function () {
          FB.api({
              method: 'Auth.revokeAuthorization'
          }, function (response) {
              clearDisplay();
          });
      });

      // no user, clear display
      function clearDisplay() {
          $('#user-info').hide('fast');
      }

      // handle a session response from any of the auth related calls
      function handleSessionResponse(response) {
          // if we dont have a session, just hide the user info
          if (!response.session) {
             console.log(response);
              return;
          }

          // if we have a session, query for the user's profile picture and name
          FB.api({
              method: 'fql.query',
              query: 'SELECT name, pic FROM profile WHERE id=' + FB.getSession().uid
          },

          function (response) {
              var user = response[0];
              $('#user-info').html('<img src="' + user.pic + '">' + user.name).show('fast');
          });
      }
  </script>