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
    var row = jQuery("<tr>" +
    "<td>" + city.name + "</td>" +
    "<td>" + city.population + "</td>" +
    "<td>Edit</td>" +
    "<td>" + "<a href=\"#\" id=\"" + city.id + "\" class=\"del\">Delete</a></td></tr>");
    $("#table_body").append(row);
}
var populateTable = function (cities) {
    for (var i = 0; i < cities.length; i++) {
        var city = cities[i];
        appendCity(city);
    }
};

function clearTable() {
    $('#table_body').text("");
}

function resetForm($form) {
    $form.find('input:text, input:password, input:file, select, textarea').val('');
    $form.find('input:radio, input:checkbox')
        .removeAttr('checked').removeAttr('selected');
    $('#add_city_form_population').val('');
}

$(document).ready(function () {
    showAll();

    $('#add_city_form').submit(function (event) {
        event.preventDefault();
        addCity();
    });
});

$(document).on('click', '.del', function () {
    var $elem = $(this);
    var id = $elem.attr('id');
    var r = confirm("Are you sure want to delete " + id);
    if (r == true) {
        function del (id) {
            $.ajax({
                url: '/city/' + id,
                type: 'DELETE',
                success: function(result) {
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



