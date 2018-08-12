var gridOptions = {
    // define grid columns
    columnDefs: [
        // using default ColDef
        {headerName: 'Country', field: 'country'},
        {headerName: 'Country extended', field: 'country extended'},
        {headerName: 'ICAO', field: 'ICAO'},
        {headerName: 'Waypoint ID', field: 'WPT_id'},
        {headerName: 'Latitude', field: 'latitude'},
        {headerName: 'Longitude', field: 'longitude'},
    ],

    // default ColDef, gets applied to every column
    defaultColDef: {
        // set the default column width
        width: 150,
        // make every column editable
        editable: true,
        // make every column use 'text' filter by default
        filter: 'agTextColumnFilter'
    },

    // default ColGroupDef, get applied to every column group
    defaultColGroupDef: {
        marryChildren: true
    },

    // define specific column types
    columnTypes: {
        numberColumn: {width: 83, filter: 'agNumberColumnFilter'},
        medalColumn: {width: 100, columnGroupShow: 'open', suppressFilter: true},
        nonEditableColumn: {editable: false},
        dateColumn: {
            // specify we want to use the date filter
            filter: 'agDateColumnFilter',

            // add extra parameters for the date filter
            filterParams: {
                // provide comparator function
                comparator: function(filterLocalDateAtMidnight, cellValue) {
                    // In the example application, dates are stored as dd/mm/yyyy
                    // We create a Date object for comparison against the filter date
                    var dateParts = cellValue.split('/');
                    var day = Number(dateParts[2]);
                    var month = Number(dateParts[1]) - 1;
                    var year = Number(dateParts[0]);
                    var cellDate = new Date(day, month, year);

                    // Now that both parameters are Date objects, we can compare
                    if (cellDate < filterLocalDateAtMidnight) {
                        return -1;
                    } else if (cellDate > filterLocalDateAtMidnight) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    },

    rowData: null,
    enableFilter: true,
    floatingFilter: true
};

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);

    agGrid.simpleHttpRequest({url: 'https://raw.githubusercontent.com/ag-grid/ag-grid-docs/master/src/olympicWinnersSmall.json'}).then(function(data) {
        gridOptions.api.setRowData(data);
    });
});