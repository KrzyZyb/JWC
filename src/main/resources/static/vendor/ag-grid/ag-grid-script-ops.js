var gridOptionsOps = {
    // define grid columns
    columnDefs: [
        // using default ColDef
        {headerName: 'Country', field: 'country'},
        {headerName: 'ICAO', field: 'icao', width: 100},
        {headerName: 'Waypoint ID', field: 'wpt_id', width: 100},
        {headerName: 'Latitude', field: 'latitude',width: 120},
        {headerName: 'Longitude', field: 'longitude',width: 120}
    ],

    // default ColDef, gets applied to every column
    defaultColDef: {
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
    var gridDivOps = document.querySelector('#myGridOps');
    new agGrid.Grid(gridDivOps, gridOptionsOps);

    agGrid.simpleHttpRequest({url: '/api/opswaypoints'}).then(function(data) {
        gridOptionsOps.api.setRowData(data);
    });
});