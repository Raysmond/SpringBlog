(function (root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module unless amdModuleId is set
    define('simditor-markdown', ["jquery","simditor","to-markdown","marked"], function (a0,b1,c2,d3) {
      return (root['SimditorMarkdown'] = factory(a0,b1,c2,d3));
    });
  } else if (typeof exports === 'object') {
    // Node. Does not work with strict CommonJS, but
    // only CommonJS-like environments that support module.exports,
    // like Node.
    module.exports = factory(require("jquery"),require("simditor"),require("to-markdown"),require("marked"));
  } else {
    root['SimditorMarkdown'] = factory(jQuery,Simditor,toMarkdown,marked);
  }
}(this, function ($, Simditor, toMarkdown, marked) {

var SimditorMarkdown,
  extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
  hasProp = {}.hasOwnProperty;

SimditorMarkdown = (function(superClass) {
  extend(SimditorMarkdown, superClass);

  function SimditorMarkdown() {
    return SimditorMarkdown.__super__.constructor.apply(this, arguments);
  }

  SimditorMarkdown.prototype.name = 'markdown';

  SimditorMarkdown.prototype.icon = 'markdown';

  SimditorMarkdown.prototype.needFocus = false;

  SimditorMarkdown.prototype._init = function() {
    SimditorMarkdown.__super__._init.call(this);
    this.markdownEl = $('<div class="markdown-editor" />').insertBefore(this.editor.body);
    this.textarea = $('<textarea/>').attr('placeholder', this.editor.opts.placeholder).appendTo(this.markdownEl);
    this.textarea.on('focus', (function(_this) {
      return function(e) {
        return _this.editor.el.addClass('focus');
      };
    })(this)).on('blur', (function(_this) {
      return function(e) {
        return _this.editor.el.removeClass('focus');
      };
    })(this));
    this.editor.on('valuechanged', (function(_this) {
      return function(e) {
        if (!_this.editor.markdownMode) {
          return;
        }
        return _this._initMarkdownValue();
      };
    })(this));
    this.markdownChange = this.editor.util.throttle((function(_this) {
      return function() {
        _this._autosizeTextarea();
        return _this._convert();
      };
    })(this), 200);
    if (this.editor.util.support.oninput) {
      this.textarea.on('input', (function(_this) {
        return function(e) {
          return _this.markdownChange();
        };
      })(this));
    } else {
      this.textarea.on('keyup', (function(_this) {
        return function(e) {
          return _this.markdownChange();
        };
      })(this));
    }
    if (this.editor.opts.markdown) {
      return this.editor.on('initialized', (function(_this) {
        return function() {
          return _this.el.mousedown();
        };
      })(this));
    }
  };

  SimditorMarkdown.prototype.status = function() {};

  SimditorMarkdown.prototype.command = function() {
    var button, i, len, ref;
    this.editor.blur();
    this.editor.el.toggleClass('simditor-markdown');
    this.editor.markdownMode = this.editor.el.hasClass('simditor-markdown');
    if (this.editor.markdownMode) {
      this.editor.inputManager.lastCaretPosition = null;
      this.editor.hidePopover();
      this.editor.body.removeAttr('contenteditable');
      this._initMarkdownValue();
    } else {
      this.textarea.val('');
      this.editor.body.attr('contenteditable', 'true');
    }
    ref = this.editor.toolbar.buttons;
    for (i = 0, len = ref.length; i < len; i++) {
      button = ref[i];
      if (button.name === 'markdown') {
        button.setActive(this.editor.markdownMode);
      } else {
        button.setDisabled(this.editor.markdownMode);
      }
    }
    return null;
  };

  SimditorMarkdown.prototype._initMarkdownValue = function() {
    this._fileterUnsupportedTags();
    this.textarea.val(toMarkdown(this.editor.getValue(), {
      gfm: true
    }));
    return this._autosizeTextarea();
  };

  SimditorMarkdown.prototype._autosizeTextarea = function() {
    this._textareaPadding || (this._textareaPadding = this.textarea.innerHeight() - this.textarea.height());
    return this.textarea.height(this.textarea[0].scrollHeight - this._textareaPadding);
  };

  SimditorMarkdown.prototype._convert = function() {
    var markdownText, text;
    text = this.textarea.val();
    markdownText = marked(text);
    this.editor.textarea.val(markdownText);
    this.editor.body.html(markdownText);
    this.editor.formatter.format();
    return this.editor.formatter.decorate();
  };

  SimditorMarkdown.prototype._fileterUnsupportedTags = function() {
    return this.editor.body.find('colgroup').remove();
  };

  return SimditorMarkdown;

})(Simditor.Button);

Simditor.Toolbar.addButton(SimditorMarkdown);

return SimditorMarkdown;

}));
