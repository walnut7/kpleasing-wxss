var ScrollFix = function(elem) {
    // Variables to track inputs
    var startY, startTopScroll;

    elem = elem || document.querySelector(elem);

    // If there is no element, then do nothing  
    if (!elem)
        return;

    // Handle the start of interactions
    elem.addEventListener('touchstart', function(event) {
        startY = event.touches[0].pageY;
        startTopScroll = elem.scrollTop;

        if (startTopScroll <= 0)
            elem.scrollTop = 1;

        if (startTopScroll + elem.offsetHeight >= elem.scrollHeight)
            elem.scrollTop = elem.scrollHeight - elem.offsetHeight - 1;
    }, false);
};

//rem
(function(doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function() {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if (clientWidth >= 640) {
                docEl.style.fontSize = '100px';
            } else {
                docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);