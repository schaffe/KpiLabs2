var citiesList;

var sortParams = {
    column: null,
    desc: false,
    nextOrder: function (column) {
        console.log(column);
        console.log(this.column);
        console.log(this.desc);

        if (column != this.column) {
            this.column = column;
            return this.desc = false, this.desc;
        } else {
            this.column = column;
            return this.desc = !this.desc, this.desc;
        }
    },
    keepOrder: function () {
        this.desc = !this.desc;
    }
};

var addCity = function () {
    var $form = $('#add_city_form');
    $.post($form.attr('action'), $form.serialize())
        .done(function (city) {
            resetForm($form);
            citiesList.push(city);
            sortParams.keepOrder();
            sortByColumn(sortParams.column, true)
        })
};


var sortByColumn = function (column) {
    switch (column) {
        case 'name' :
            citiesList.sort(sortBy(column, sortParams.nextOrder(column), function (a) {
                return a.toUpperCase()
            }));
            break;
        case 'population':
            citiesList.sort(sortBy(column, !sortParams.nextOrder(column), parseInt));
            break;
    }

    clearTable();
    populateTable(citiesList);
};

var sortBy = function (field, reverse, primer) {

    var key = primer ?
        function (x) {
            return primer(x[field])
        } :
        function (x) {
            return x[field]
        };

    reverse = !reverse ? 1 : -1;

    return function (a, b) {
        return a = key(a), b = key(b), reverse * ((a > b) - (b > a));
    }
};

var showAll = function () {
    $.get("/city").done(function (cities) {
        citiesList = cities;
        //if(sort) {
        //    citiesList.sort_by(sort);
        //}
        populateTable(citiesList);
    });
};

function appendCity(city) {
    var row = jQuery("<tr id=\"" + city.id + "\" >" +
    "<td>" + city.name + "</td>" +
    "<td>" + city.population + "</td>" +
    "<td>" + "<a href=\"#\" class=\"edit\">Edit</a></td>" +
    "<td>" + "<a href=\"#\" class=\"del\">Delete</a></td></tr>");
    $("#table_body").append(row);
}
var populateTable = function (cities) {
    for (var i = 0; i < cities.length; i++) {
        var city = cities[i];
        appendCity(city);
    }
};

function clearTable() {
    $('#table_body').empty();
}

function resetForm($form) {
    $form.find('input:text, input:password, input:file, select, textarea').val('');
    $form.find('input:radio, input:checkbox')
        .removeAttr('checked').removeAttr('selected');
    $('#add_city_form_population').val('');
}

function changeBg(cityName) {
    //var ipQuery = "http://ip.jsontest.com/";
    //$.get(ipQuery).done(function (result) {
    //    var query = 'http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=' + cityName + '&imgsz=huge&userip=' + result.ip;
    //    $.get(query).done(function (result) {
    //        var img = result[0].url;
    //        document.body.style.background = "#f3f3f3 url('" + img + "') no-repeat right top";
    //    }).error(function(data) {
    //        alert("error");
    //    });
    //});
    //
    //$.getJSON("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=PHP",
    //    function(data){
    //        var ul = document.createElement("ul");
    //        $.each(data.results, function(i, val){
    //            var li = document.createElement("li");
    //            li.innerHTML = '<a href="'+val.url+'" title="'+val.url+'" target="_blank">'+val.title+"</a> - "+val.content;
    //            ul.appendChild(li);
    //        });
    //        $('body').html(ul);
    //    });
    $('.bg').css('background-image', 'url(https://everythingabya.files.wordpress.com/2013/09/colosseum-taly-rome-landscape1.jpg)');
    //$(".bg").background = "url('http://upload.wikimedia.org/wikipedia/en/4/49/Trevi_Fountain_Rome_(capital_edit).jpg')";


}


function getCityFromList(id) {
    for (var i = 0; i < citiesList.length; i++) {
        if (citiesList[i].id == id) {
            return citiesList[i];
        }
    }
}

var selectedId;
function showPopupCity(id){
    var popUp = document.getElementById("popupcontent");
    popUp.style.visibility = "visible";;
    selectedId = id;
    var $name = $("#edit_city_form_name");
    var $population = $("#edit_city_form_population");

    var city = getCityFromList(id);

    $name.val(city.name);
    $population.val(city.population);
}

function editCity() {
    var id = selectedId;
    var $name = $("#edit_city_form_name");
    var $population = $("#edit_city_form_population");

    var name = $name.val();
    var pop = $population.val();

    var city = {name: name, population: pop};

    $.ajax({
        url: '/city/' + id,
        data: city,
        type: 'PUT',
        success: function () {
            hidePopup();
            clearTable();
            showAll();
        }
    });
}

function hidePopup(){
    var popUp = document.getElementById("popupcontent");
    popUp.style.visibility = "hidden";
}

$(document).ready(function () {
    showAll();

    $('#add_city_form').submit(function (event) {
        event.preventDefault();
        addCity();
    });

    $('#edit_city_form').submit(function (event) {
        event.preventDefault();
        editCity();
    });
});

$(document).on('click', '.del', function () {
    var $elem = $(this);
    var id = $elem.parent().parent().attr('id');
    var r = confirm("Are you sure want to delete " + id);
    if (r == true) {
        function del(id) {
            $.ajax({
                url: '/city/' + id,
                type: 'DELETE',
                success: function () {
                    for (var i = 0; i < citiesList.length; i++) {
                        if (citiesList[i].id == id) {
                            citiesList.splice(i, 1);
                            $elem.parent().parent().remove();
                        }
                    }
                }
            });
        }

        del(id);
    }
});

$(document).on('click', '.edit', function () {
    var $elem = $(this);
    var id = $elem.parent().parent().attr('id');
    showPopupCity(id);
});

$(document).on('click', 'tr', function () {
    var $elem = $(this);
    var id = $elem.attr('id');
    var cityName = getCityFromList(id).name;
    console.log(id + " " + cityName)
    changeBg(cityName);
});



