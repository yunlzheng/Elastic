/**
 * content.js
 *
 * SMA Applied Tech Ltd. All Right Reserved
 *
 * Author: ChenXin <chenxin@smapp.hk>
 *
**/(function(e){e("#menuLayer").on("click",".chilBut",function(){e("#menuLayer .chilBut.chilOver").removeClass("chilOver");e(this).addClass("chilOver")})})(jQuery);$(document).ready(function(){var e=function(){$("#contentLayerFrameWrapper").width($("body").width()-$("#menuLayer").width())};e();$(window).resize(function(){e()})});