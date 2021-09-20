$(document).ready(function(){
    $('input[type="radio"]').click(function(){
        var inputValue = $(this).attr("value");
        var targetForm = $("." + inputValue);
        $(".formu").not(targetForm).hide();
        $(targetForm).show();
    });
});
