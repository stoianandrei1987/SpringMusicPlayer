$(document).ready(function () {
    $('.lgi-link').hide();
})


function fireLogin() {


    console.info("Attempting to authenticate using : " + $('#login-form').serialize());

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/login',
        data: $('#login-form').serialize(),
        cache: false,
        dataType: "json",
        crossDomain: false,
        success: function (data) {
            var response = jQuery.parseJSON(data);
            if (response.success == true) {
                $('.nlg-stuff').hide();
                $('.lgi-link').show();
                alert("Authentication Success! data :" + data);

            }
            else {
                alert("Unable to login");
            }
        },
        error: function (data) {
            alert("Login failure");
        }
    });



}

function hideLoggedInBtns() {
    $('.lgi-link').hide();
    $('.nlg-stuff').show();
}