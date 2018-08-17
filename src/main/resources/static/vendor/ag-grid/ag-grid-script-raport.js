var gridOptionsRaport = {
    // define grid columns
    columnDefs: [
        // using default ColDef
        {headerName: 'Country', field: 'country'},
        {headerName: 'ICAO', field: 'ICAO'},
        {headerName: 'Waypoint ID', field: 'WPT_id'},
        {headerName: 'Latitude', field: 'latitude'},
        {headerName: 'Longitude', field: 'longitude'},
        {headerName: 'Status', field: 'status'},
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
        nonEditableColumn: {editable: false},
    },

    rowData: null,
    enableFilter: true,
    floatingFilter: true
};

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
    var gridDivRaport = document.querySelector('#myGridRaport');
    new agGrid.Grid(gridDivRaport, gridOptionsRaport);

    agGrid.simpleHttpRequest({url: '/api/raportwaypoints'}).then(function(data) {
        gridOptionsRaport.api.setRowData(data);
    });
});