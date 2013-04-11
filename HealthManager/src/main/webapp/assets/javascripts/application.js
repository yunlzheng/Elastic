/**
 * application.js
 *
 * SMA Applied Tech Ltd. All Right Reserved
 *
 * Author: ChenXin <chenxin@smapp.hk>
 *
**/

$(document).ready(function(){
  var match_small_screen = function() {
    if ($(window).height() < 700) {
      $('body').addClass('small-screen');
    } else {
      $('body').removeClass('small-screen');
    }
  };

  match_small_screen();
  $(window).resize(function() {
    match_small_screen();
  });
});