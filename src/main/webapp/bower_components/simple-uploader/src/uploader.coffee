
class Uploader extends SimpleModule

  @count: 0

  opts:
    url: ''
    params: null
    fileKey: 'upload_file'
    connectionCount: 3

  _init: ->
    @files = [] #files being uploaded
    @queue = [] #files waiting to be uploaded
    @id = ++ Uploader.count

    # upload the files in the queue
    @on 'uploadcomplete', (e, file) =>
      @files.splice($.inArray(file, @files), 1)
      if @queue.length > 0 and @files.length < @opts.connectionCount
        @upload @queue.shift()
      else if @files.length == 0
        @uploading = false

    # confirm to leave page while uploading
    $(window).on 'beforeunload.uploader-' + @id, (e) =>
      return unless @uploading

      # for ie
      # TODO firefox can not set the string
      e.originalEvent.returnValue = @_t('leaveConfirm')
      # for webkit
      return @_t('leaveConfirm')

  generateId: (->
    id = 0
    return ->
      id += 1
  )()

  upload: (file, opts = {}) ->
    return unless file?

    if $.isArray(file) or file instanceof FileList
      @upload(f, opts) for f in file
    else if $(file).is('input:file')
      key = $(file).attr('name')
      opts.fileKey = key if key
      @upload($.makeArray($(file)[0].files), opts)
    else if !file.id or !file.obj
      file = @getFile file

    return unless file and file.obj

    $.extend(file, opts)

    if @files.length >= @opts.connectionCount
      @queue.push file
      return

    return if @triggerHandler('beforeupload', [file]) == false

    @files.push file
    @_xhrUpload file
    @uploading = true

  getFile: (fileObj) ->
    if fileObj instanceof window.File or fileObj instanceof window.Blob
      name = fileObj.fileName ? fileObj.name
    else
      return null

    id: @generateId()
    url: @opts.url
    params: @opts.params
    fileKey: @opts.fileKey
    name: name
    size: fileObj.fileSize ? fileObj.size
    ext: if name then name.split('.').pop().toLowerCase() else ''
    obj: fileObj

  _xhrUpload: (file) ->
    formData = new FormData()
    formData.append(file.fileKey, file.obj)
    formData.append("original_filename", file.name)
    formData.append(k, v) for k, v of file.params if file.params

    file.xhr = $.ajax
      url: file.url
      data: formData
      processData: false
      contentType: false
      type: 'POST'
      headers:
        'X-File-Name': encodeURIComponent(file.name)
      xhr: ->
        req = $.ajaxSettings.xhr()
        if req
          req.upload.onprogress = (e) =>
            @progress(e)
        req
      progress: (e) =>
        return unless e.lengthComputable
        @trigger 'uploadprogress', [file, e.loaded, e.total]
      error: (xhr, status, err) =>
        @trigger 'uploaderror', [file, xhr, status]
      success: (result) =>
        @trigger 'uploadprogress', [file, file.size, file.size]
        @trigger 'uploadsuccess', [file, result]
        $(document).trigger 'uploadsuccess', [file, result, @]
      complete: (xhr, status) =>
        @trigger 'uploadcomplete', [file, xhr.responseText]

  cancel: (file) ->
    unless file.id
      for f in @files
        if f.id == file * 1
          file = f
          break

    @trigger 'uploadcancel', [file]

    # abort xhr will trigger complete event automatically
    file.xhr.abort() if file.xhr
    file.xhr = null

  readImageFile: (fileObj, callback) ->
    return unless $.isFunction callback

    img = new Image()
    img.onload = ->
      callback img
    img.onerror = ->
      callback()

    if window.FileReader && FileReader.prototype.readAsDataURL && /^image/.test(fileObj.type)
      fileReader = new FileReader()
      fileReader.onload = (e) ->
        img.src = e.target.result
      fileReader.readAsDataURL fileObj
    else
      callback()

  destroy: ->
    @queue.length = 0
    @cancel file for file in @files
    $(window).off '.uploader-' + @id
    $(document).off '.uploader-' + @id

  @i18n:
    'zh-CN':
      leaveConfirm: '正在上传文件，如果离开上传会自动取消'

  @locale: 'zh-CN'


uploader = (opts) ->
  new Uploader(opts)


