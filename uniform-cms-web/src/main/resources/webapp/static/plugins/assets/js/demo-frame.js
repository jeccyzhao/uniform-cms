/**
 * Created by ejacob on 3/30/2016.
 */


require(['jquery','wulf/navbar'], function($) {
    $(document).ready(function() {
        // Activate the first tab by default
        document.getElementById("nav_home").className += ' active';
        // If need to clear activation from banner tabs
        //$ ('#wulf_contacts').click (function () {
        //    clearTabActivation ();
        //});
        // Receiving the tab change message caused by buttons inside iframe
        window.addEventListener("message", receiveMessage, false);

        function receiveMessage(event)
        {
            var origin = event.origin || event.originalEvent.origin; // For Chrome, the origin property is in the event.originalEvent object.
            // Sender is in local use: file for IE/FF, null for Chrome
            // Sender is in server use: wulf-demo
            // TODO make null accepted only for Chrome case
            if (origin !== "null" && origin !== "file:" && origin !== "http://wulf-demo.dynamic.nsn-net.net") {
                return;
            }
            // Actual messages to be handled. Remove the activation from current tab and activate the new tab
            if (event.data === "nav_download" || event.data === "nav_get_started") {
                clearTabActivation ();
                document.getElementById (event.data).className += ' active';
            }
            // TODO Also if someone clicks inside the iframe, we need to close the possibly open banner menu tree
        }

        function clearTabActivation () {
            var elements = document.getElementsByClassName ("n-banner-3Link");
            for (var i = 0; i < elements.length; i++) {
                if ($(elements[i]).hasClass('active')) {
                    elements[i].classList.remove ('active');
                }
            }
        }

        //1. response on click in menu items
        //2. draggable
        //3. responsive on window resize
        var isResizing = false,
            lastDownX = 0;
        var winW = $ (window).width ();
        $ (function () {
            var container = $ ('.wrapper'),
                left = $ ('.menu'),
                right = $ ('.content-box'),
                handle = $ ('#drag'),
                move = 0;
            // mouse down -> start listening on mouse movement
            handle.on ('mousedown', function (e) {
                isResizing = true;
                lastDownX = e.clientX;
                handle.addClass ('dragging');
            });

            // mouse move when down -> re-position left/right panel
            $ (document).on ('mousemove', function (e) {
                if (!isResizing) {
                    return;
                }
                move = e.clientX - container.offset ().left;
                calcMove (container.width (), move);
            }).on ('mouseup', function (e) {
                isResizing = false;
                handle.removeClass ('dragging');
            });

            // re-position everything on window resize
            $ (window).on ('resize', function () {
                calcMove (container.width (), move);
            });

            // layout the container
            // min~max moves are between 150px to container width - 150px
            function calcMove (conWidth, move) {
                var minMove = 150;
                var spacingOffset = 20;
                var maxMove = conWidth - minMove;
                move = move < minMove ? minMove : move;
                move = move > maxMove ? maxMove : move;
                var offsetRight = conWidth - move;
                left.css ('right', offsetRight);
                right.css ('width', offsetRight - spacingOffset);
            }
        });

        $('#main_content').attr('src', "demo-home.html");
    });
});

