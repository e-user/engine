// -*- mode: js; indent-tabs-mode: nil; -*-
define (['engine/window', 'jquery'],
function (window,          $) {
  var instance;

  return {
    instance: instance,
    create: function (spec) {
      var that = window.create($.extend({}, { bufferName: '*minibuffer*', loader: 'load-minibuffer', highline: false }, spec)),
          active = false, _focus = that.focus;

      that.focus = function () {
        if (active) {
          _focus();
        } else {
          $.pnotify({
            text: 'Minibuffer window is not active',
            type: 'info',
            icon: 'ui-icon ui-icon-info'
          });
        }
      };

      $(that.container).parent().height(that.renderer.lineHeight);
      instance = that;
      return that;
    }
  };
});
