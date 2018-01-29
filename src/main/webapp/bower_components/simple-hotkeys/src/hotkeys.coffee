
class Hotkeys extends SimpleModule
  @count: 0
  @keyNameMap:
    # Keys with words or arrows on them
    8:"Backspace", 9:"Tab", 13:"Enter", 16:"Shift", 17:"Control", 18:"Alt",
    19:"Pause", 20:"CapsLock", 27:"Esc", 32:"Spacebar", 33:"PageUp",
    34:"PageDown", 35:"End", 36:"Home", 37:"Left", 38:"Up", 39:"Right",
    40:"Down", 45:"Insert", 46:"Del", 91: "Meta", 93: "Meta",

    # Number keys on main keyboard (not keypad)
    48:"0",49:"1",50:"2",51:"3",52:"4",53:"5",54:"6",55:"7",56:"8",57:"9",

    # Letter keys. Note that we don't distinguish upper and lower case
    # PS. String.fromCharCode
    65:"A", 66:"B", 67:"C", 68:"D", 69:"E", 70:"F", 71:"G", 72:"H", 73:"I",
    74:"J", 75:"K", 76:"L", 77:"M", 78:"N", 79:"O", 80:"P", 81:"Q", 82:"R",
    83:"S", 84:"T", 85:"U", 86:"V", 87:"W", 88:"X", 89:"Y", 90:"Z",

    # Keypad numbers and punctuation keys. (Opera does not support these.)
    96:"0",97:"1",98:"2",99:"3",100:"4",101:"5",102:"6",103:"7",104:"8",105:"9",
    106:"Multiply", 107:"Add", 109:"Subtract", 110:"Decimal", 111:"Divide",

    # Function keys
    112:"F1", 113:"F2", 114:"F3", 115:"F4", 116:"F5", 117:"F6",
    118:"F7", 119:"F8", 120:"F9", 121:"F10", 122:"F11", 123:"F12",
    124:"F13", 125:"F14", 126:"F15", 127:"F16", 128:"F17", 129:"F18",
    130:"F19", 131:"F20", 132:"F21", 133:"F22", 134:"F23", 135:"F24",

    # Punctuation keys that don't require holding down Shift
    # Hyphen is nonportable: FF returns same code as Subtract
    59:";", 61:"=", 186:";", 187:"=", # Firefox and Opera return 59,61 
    188:",", 190:".", 191:"/", 192:"`", 219:"[", 220:"\\", 221:"]", 222:"'"

  @aliases:
    "escape":"esc",
    "delete":"del",
    "return":"enter",
    "ctrl":"control",
    "space":"spacebar",
    "ins":"insert",
    "cmd": "meta",
    "command": "meta",
    "wins": "meta",
    "windows": "meta"

  @normalize: (shortcut) ->
    keys = shortcut.toLowerCase().replace(/\s+/gi, "").split "+"
    keys[i] = @aliases[key] or key for key, i in keys
    keyname = keys.pop()
    keys.sort().push keyname
    keys.join "_"

  opts:
    el: document

  _init: ->
    @id = ++ @constructor.count
    @_map = {}
    @_delegate = if typeof @opts.el is "string" then document else @opts.el
    $(@_delegate).on "keydown.simple-hotkeys-#{@id}", @opts.el, (e) =>
      @_getHander(e)?.call this, e

  _getHander: (e) ->
    return unless keyname = @constructor.keyNameMap[e.which]
    shortcut = ""
    shortcut += "alt_" if e.altKey
    shortcut += "control_" if e.ctrlKey
    shortcut += "meta_" if e.metaKey
    shortcut += "shift_" if e.shiftKey
    shortcut += keyname.toLowerCase()
    @_map[shortcut]

  respondTo: (subject) ->
    if typeof subject is 'string'
      @_map[@constructor.normalize subject]?
    else
      @_getHander(subject)?

  add: (shortcut, handler) ->
    @_map[@constructor.normalize shortcut] = handler
    @

  remove: (shortcut) ->
    delete @_map[@constructor.normalize shortcut]
    @

  destroy: ->
    $(@_delegate).off ".simple-hotkeys-#{@id}"
    @_map = {}
    @

hotkeys = (opts) ->
  new Hotkeys(opts)
