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
      $this = $(this)
      paragraphKey = $this.parent().data('paragraph')
      paragraphIndex = paragraphKey - 1
      alignedParagraph = that.paragraphComparisons[paragraphIndex]

      $('tr[data-paragraph] td').css("background-color": "initial")
      $('tr[data-paragraph] td.paragraph-percents').html('0.00 %')
      $("tr[data-paragraph='#{paragraphKey}'] td").css("background-color": "rgb(200, 255, 200)")
      percents = "#{(alignedParagraph.probability * 100).toFixed(2)} %"
      $("tr[data-paragraph='#{paragraphKey}'] td.paragraph-percents").html(percents)
