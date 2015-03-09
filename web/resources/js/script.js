var citiesList;
var sortParams = {
    column: null,
    desc: false,
    nextOrder: function(column) {
        console.log(column);
        console.log(this.column);
        console.log(this.desc);

        if(column != this.column) {
            this.column = column;
            return this.desc = false, this.desc;
        } else {
            this.column = column;
            return this.desc = !this.desc, this.desc;
        }
    }
};

var sortField = function (column) {
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

var showAll = function (sort) {
    $.get("/city").done(function (cities) {
        citiesList = cities;
        //if(sort) {
        //    citiesList.sort_by(sort);
        //}
        populateTable(citiesList);
    });
};

var populateTable = function (cities) {
    for (var i = 0; i < cities.length; i++) {
        var city = cities[i];
        $("#table_body").append("<tr>" +
        "<td>" + city.name + "</td>" +
        "<td>" + city.population + "</td>" +
        "<td>Edit</td>" +
        "<td>" + "<a href=\"#\" id=\"" + city.id + "\" class=\"del\">Delete</a></td></tr>");
    }
};

function clearTable() {
    $('#table_body').text("");
}

$(document).ready(function () {
    showAll();
});





