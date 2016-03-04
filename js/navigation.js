function setCookie(key, value) {
	  Cookies.set(key, value, { expires: 3 });
  }

  function getCookie(key) {
      var value = Cookies.get(key);
      return value;
  }
  
  $(document).ready(function($) {
    // read status of all titles from cookie array, 
    // if none found, set to expanded false
    $('#accordion').find('.sidebarTitle').each(
    		  function() {
    			  showSection = getCookie(this.id);
    			  if (showSection == "show" ) {
    			        $(this).next().show();
  			      } else {
    			        //$(this).next().slideToggle('fast');
    			        $(this).next().hide();
    			    }
    			}		
    );
    // expanded and contract as set in the array
    
	  // set onClick event for all titles
	  $('#accordion').find('.sidebarTitle').click(function(){
      if($(this).next().is(':visible')) {
    	  setCookie(this.id, "hide")
      } else {
        setCookie(this.id, "show")
      }
      $(this).next().slideToggle('fast');
    });
  });