window.colorizeGroups = (e)->
  $target = $(e.currentTarget)
  $group = $target.find('.native-paragraph-group')

  $(".paragraph-group").each (i, el)->
    $el = $(el)
    if $el.val() != $group.val()
      color = 'initial'
    else
      color = 'lightgreen'

    $cell = null
    if $el.hasClass('native-paragraph-group')
      $cell = $(el).closest('tr').find('td.native-paragraph')
    else
      $cell = $(el).closest('tr').find('td.foreign-paragraph')

    $cell.css('background-color', color)
