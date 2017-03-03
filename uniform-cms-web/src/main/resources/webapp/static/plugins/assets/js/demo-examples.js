/**
 * Created by ejacob on 3/30/2016.
 */



$(document).ready(function() {

    var hash = location.hash;

    $(window).on('hashchange', function () {
        location.reload();
    });

    $('#content').attr('src', "components/common.html");
    $('#menu').attr('src', "demo-examples-menu.html" + hash);

    var startMenu, menuWidth,contentWdith,
        menu = $(".demo-example-menu-box-wrapper").get(0),
        content = $(".demo-example-content-box-wrapper").get(0);

    function initResize(e) {
        startMenu = e.clientX;
        menuWidth = parseInt(document.defaultView.getComputedStyle(menu).width, 10);
        contentWdith = parseInt(document.defaultView.getComputedStyle(content).width, 10);
        document.documentElement.addEventListener('mousemove', doResize, false);
        document.documentElement.addEventListener('mouseup', stopResize, false);
    }

    function doResize(e) {
        menu.style.width = (menuWidth + e.clientX - startMenu) + 'px';
        content.style.width = (contentWdith - e.clientX + startMenu) + 'px';
        e.preventDefault();
    }

    function stopResize() {
        document.documentElement.removeEventListener('mousemove', doResize, false);
        document.documentElement.removeEventListener('mouseup', stopResize, false);
    }

    //can't add mouseup event on iframe section, need comment out below statement to explicitly
    //add eventlistener on iframe. But it will raise security error in Chrome.
    //$('iframe').load(function() {
    //    $(this).contents().find("body").on('mouseup', stopResize);
    //});

    $(".demo-resize").get(0).addEventListener('mousedown', initResize, false);
});

$(window).resize(function() {
    var totalWidth = $(".demo-body-content").width();
    var menuWidth = $(".demo-example-menu-box-wrapper").width();
    $(".demo-example-content-box-wrapper").width(totalWidth - menuWidth);

});

$(window).load(function() {
    var totalWidth = $(".demo-body-content").width();
    var menuWidth = $(".demo-example-menu-box-wrapper").width();
    $(".demo-example-content-box-wrapper").width(totalWidth - menuWidth);
});