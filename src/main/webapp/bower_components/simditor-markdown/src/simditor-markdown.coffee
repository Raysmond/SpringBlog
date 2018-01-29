
class SimditorMarkdown extends Simditor.Button

  name: 'markdown'

  icon: 'markdown'

  needFocus: false

  _init: ->
    super()

    @markdownEl = $ '<div class="markdown-editor" />'
      .insertBefore @editor.body
    @textarea = $ '<textarea/>'
      .attr('placeholder', @editor.opts.placeholder)
      .appendTo @markdownEl

    @textarea.on 'focus', (e) =>
      @editor.el.addClass('focus')
    .on 'blur', (e) =>
      @editor.el.removeClass('focus')

    @editor.on 'valuechanged', (e) =>
      return unless @editor.markdownMode
      @_initMarkdownValue()

    @markdownChange = @editor.util.throttle =>
      @_autosizeTextarea()
      @_convert()
    , 200

    if @editor.util.support.oninput
      @textarea.on 'input', (e) =>
        @markdownChange()
    else
      @textarea.on 'keyup', (e) =>
        @markdownChange()

    if @editor.opts.markdown
      @editor.on 'initialized', =>
        @el.mousedown()

  status: ->

  command: ->
    @editor.blur()
    @editor.el.toggleClass 'simditor-markdown'
    @editor.markdownMode = @editor.el.hasClass 'simditor-markdown'

    if @editor.markdownMode
      @editor.inputManager.lastCaretPosition = null
      @editor.hidePopover()
      @editor.body.removeAttr 'contenteditable'
      @_initMarkdownValue()
    else
      @textarea.val ''
      @editor.body.attr 'contenteditable', 'true'

    for button in @editor.toolbar.buttons
      if button.name == 'markdown'
        button.setActive @editor.markdownMode
      else
        button.setDisabled @editor.markdownMode

    null

  _initMarkdownValue: ->
    @_fileterUnsupportedTags()
    @textarea.val toMarkdown(@editor.getValue(), gfm: true)
    @_autosizeTextarea()

  _autosizeTextarea: ->
    @_textareaPadding ||= @textarea.innerHeight() - @textarea.height()
    @textarea.height(@textarea[0].scrollHeight - @_textareaPadding)

  _convert: ->
    text = @textarea.val()
    markdownText = marked(text)

    @editor.textarea.val markdownText
    @editor.body.html markdownText

    @editor.formatter.format()
    @editor.formatter.decorate()

  _fileterUnsupportedTags: ->
    @editor.body.find('colgroup').remove()


Simditor.Toolbar.addButton SimditorMarkdown
