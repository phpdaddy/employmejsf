$(document).ready(function () {
    var speed = 300;
    $(".employer-reg-block").hide();
    $(".candidate-reg-block").hide();
    $(".candidate-reg-block").slideDown(speed);

    $('.reg-usertype-radio input:radio', this).each(function () {
        $(this).change(function (event) {
            if ($(this).val() == "employer") {
                $(".employer-reg-block").slideDown(speed);
                $(".candidate-reg-block").slideUp(speed);
            } else {
                $(".candidate-reg-block").slideDown(speed);
                $(".employer-reg-block").slideUp(speed);
            }
        });
    });

});
$(document).ready(function () {
    $("form").submit(function (event) {
        randomBackground();
        var tid = setInterval(randomBackground, 100);
    });
    background();
});
$(document).ready(function () {

    $(".slideshow").owlCarousel({
        autoPlay: 2000,
        dots: true,
        singleItem: true,
        autoHeight: true,
        transitionStyle: "goDown"

    });
});

function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function background() {
    var col1 = "#795452";
    var col2 = "#7f6a6b";
    $("body").css(
        "background",
        "-webkit-linear-gradient(-90deg, " + col1 + ", " + col2
        + ")  no-repeat " + col2);

    $("body").css(
        "background",
        "-o-linear-gradient(-90deg, " + col1 + ", " + col2
        + ")  no-repeat " + col2);

    $("body").css(
        "background",
        "-moz-linear-gradient(-90deg, " + col1 + ", " + col2
        + ") no-repeat  " + col2);

    $("body").css(
        "background",
        "linear-gradient(-90deg, " + col1 + ", " + col2 + ")  no-repeat "
        + col2);
}

function randomBackground() {
    var col1 = getRandomColor();
    var col2 = getRandomColor();
    $("body").css(
        "background",
        "-webkit-linear-gradient(-90deg, " + col1 + ", " + col2
        + ")  no-repeat " + col2);

    $("body").css(
        "background",
        "-o-linear-gradient(-90deg, " + col1 + ", " + col2
        + ")  no-repeat " + col2);

    $("body").css(
        "background",
        "-moz-linear-gradient(-90deg, " + col1 + ", " + col2
        + ") no-repeat  " + col2);

    $("body").css(
        "background",
        "linear-gradient(-90deg, " + col1 + ", " + col2 + ")  no-repeat "
        + col2);
}

function abortTimer() { // to be called when you want to stop the timer
    clearInterval(tid);
}

$(document).ready(function () {
    $('.datepicker').datepicker({
        format: 'dd.mm.yyyy'
    });
});