// navigation.js
// Manfred Moser
// Requirements: 
// libraries: jquery, js.cookie
// css elements in page: accordion with nested sidebarTitle div and following content divs
// see template.html and clmBook.css

// Accordion style navigation that keeps track of hide/show status for each section
// via cookies so that each individual static page can read the state of the
// section in the nav and display them correctly



function setCookie(key, value) {
	Cookies.set(key, value, { expires: 3 });
}

$(document).ready(function($) {
  // iterate through titles, read cookie and display as set
  $('#accordion').find('.sidebarTitle').each(function() {
    showSection = Cookies.get(this.id);
    if (showSection == "show" ) {
      $(this).next().show();
    } else {
      $(this).next().hide();
    }
  });
  
  // set onClick event for all titles
  $('#accordion').find('.sidebarTitle').click(function() {
    // if its visible and was clicked
    if($(this).next().is(':visible')) {
      // set it to hidden
	  setCookie(this.id, "hide")
    } else {
      // otherwise set to be shown
      setCookie(this.id, "show")
    }
    // always toggle on click
    $(this).next().slideToggle('fast');
  });
});
