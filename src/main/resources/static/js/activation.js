function activate(id){
    $.ajax({
        type: 'PUT',
        url: 'users/activation/' + id,
        success: function (response){
            $('td').filter(function() {
                return $(this).text() == id;
            }).closest('tr').remove();
            if ($('table tbody tr').length === 0) {
                $('table').hide();
                $('#info').text('There are no users to activate');
                $('#info').show();
            }
        },
        error: function (response, exception){
            console.log(exception);
        }
    });
};

$(document).ready(function (){
    $.ajax({
        type: 'GET',
        url: 'users/inactive',
        success: function (response){
            if(response.length === 0){
                $('table').hide();
                $('#info').text('There are no users to activate');
                $('#info').show();
            }
            for(var i = 0; i < response.length; i++){
                var row = '<tr><td>' + response[i].id + '</td><td>' + response[i].email + '</td><td>' +
                    '<button type="button" onclick="activate(' + response[i].id + ')">Activate</button>';
                $('table tbody').append(row);
            }
        },
        error: function (response, exception) {
            console.log(exception);
        }
    });
});