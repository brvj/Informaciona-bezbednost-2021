function registration() {
    $('#error').hide();

    var email = $('#email').val();
    var password = $('#password').val();

    console.log(email);
    console.log(password);

    $.ajax({
        type: 'POST',
        url: 'users',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(
            {
                'email': email,
                'password': password,
            }
        ),
        success: function (response) {
            window.location.replace('http://localhost:8443');
        },
        error: function (response, exception){
            if(response.status === 409){
                $('#error').text('Email already exists');
                $('#error').show();
            }
        }
    })
}