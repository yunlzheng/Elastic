/**
 * content.js
 *
 * SMA Applied Tech Ltd. All Right Reserved
 *
 * Author: ChenXin <chenxin@smapp.hk>
 *
**/

(function($){
  $('#menuLayer').on('click', '.chilBut', function(){
    $('#menuLayer .chilBut.chilOver').removeClass('chilOver');
   
    $(this).addClass('chilOver');
  
  });
})(jQuery);

$(document).ready(function(){
  var scale_content_frame = function() {
    $('#contentLayerFrameWrapper').width($('body').width() - $('#menuLayer').width());
  };
  scale_content_frame();
  $(window).resize(function() {
    scale_content_frame();
  });
});