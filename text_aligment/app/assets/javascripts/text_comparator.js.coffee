class window.TextComparator
  constructor: (@paragraphComparisons)->
    @$nativeParagraphs = $('td.native-paragraph')
    @$foreignParagraphs = $('td.foreign-paragraph')
    @$paragraphPercents = $('td.paragraph-percents')
    @initEvents()

  initEvents: ->
    @initNativeComparison()
    @initForeignComparison()
    

  initNativeComparison: ->
    @initParagraphComparison.bind(@)(
      @$nativeParagraphs,
      @$foreignParagraphs, 
      'N', 
      'fa-arrow-right'
    )

  initForeignComparison: ->
    @initParagraphComparison.bind(@)(
      @$foreignParagraphs, 
      @$nativeParagraphs, 
      'F', 
      'fa-arrow-left'
    )

  initParagraphComparison: ($paragraphs, $otherParagraphs, paragraphPrefix, iconClass)->
    that = @
    $paragraphs.on 'mouseenter', ->
      debugger
      $this = $(this)
      paragraphKey = "#{paragraphPrefix}#{$this.data('paragraph')}"
      comparisons = that.paragraphComparisons[paragraphKey]
      $paragraphs.css("background-color": "initial")
      $otherParagraphs.css("background-color": "initial")
      $(@).css("background-color": "rgb(200, 255, 200)")
      index = 0
      for otherKey, percents of comparisons
        $otherParagraph = $( $otherParagraphs[index] )
        $paragraphPercent = $( that.$paragraphPercents[index] )
        
        $otherParagraph.css("background-color": "rgb(200, #{Math.round(200+ 55 * percents)}, 200)")
        $paragraphPercent.css("background-color": "rgb(200, #{Math.round(200+ 55 * percents)}, 200)")
        $paragraphPercent.html((Math.round(percents * 10000) / 100).toFixed(2) + "%&nbsp;<i class=\"fa #{iconClass}\"></i>" )
        index += 1
