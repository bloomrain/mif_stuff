class window.TextComparator
  constructor: (@paragraphComparisons)->
    @$nativeParagraphs = $('td.native-paragraph')
    @$foreignParagraphs = $('td.foreign-paragraph')
    @$paragraphPercents = $('td.paragraph-comparison')
    @initEvents()

  initEvents: ->
    that = @
    @$nativeParagraphs.on 'mouseenter', ->
      $this = $(this)
      paragraphKey = "N#{$this.data('paragraph')}"
      comparisons = that.paragraphComparisons[paragraphKey]
      that.$nativeParagraphs.css("background-color": "initial")
      $(@).css("background-color": "rgb(200, 255, 200)")
      index = 0
      for otherKey, percents of comparisons
        $foreignParagraph = $( that.$foreignParagraphs[index] )
        $paragraphPercent = $( that.$paragraphPercents[index] )
        $foreignParagraph.css("background-color": "rgb(200, #{Math.round(200+ 55 * percents)}, 200)")
        $paragraphPercent.css("background-color": "rgb(200, #{Math.round(200+ 55 * percents)}, 200)")
        $paragraphPercent.html((Math.round(percents * 10000) / 100).toString() + "%")
        index += 1