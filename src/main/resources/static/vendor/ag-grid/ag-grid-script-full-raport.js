var gridOptionsFullRaport = {
    // define grid columns
    columnDefs: [
        // using default ColDef
        {headerName: 'Country OPS', field: 'countryOPS', cellStyle: {'background-color': 'LightBlue'}},
        {headerName: 'ICAO OPS', field: 'icaoops', width: 100, cellStyle: {'background-color': 'LightBlue'}},
        {headerName: 'Waypoint ID OPS', field: 'wpt_idOPS', width: 120, cellStyle: {'background-color': 'LightBlue'}},
        {headerName: 'Latitude OPS', field: 'latitudeOPS',width: 120, cellStyle: {'background-color': 'LightBlue'}},
        {headerName: 'Longitude OPS', field: 'longitudeOPS',width: 120, cellStyle: {'background-color': 'LightBlue'}},
        {headerName: 'Status', field: 'status',width: 300, cellStyle: {'background-color': 'LightBlue'}},
        {headerName: 'Country JAD', field: 'countryJAD',width:300},
        {headerName: 'ICAO JAD', field: 'icaojad', width: 100},
        {headerName: 'Waypoint ID JAD', field: 'wpt_idJAD', width: 120},
        {headerName: 'Latitude JAD', field: 'latitudeJAD',width: 120},
        {headerName: 'Longitude JAD', field: 'longitudeJAD',width: 120}
    ],

    // default ColDef, gets applied to every column
    headerComponentParams : {
                        menuIcon: 'fa-bars',
                        template:
                        '<div class="ag-cell-label-container" role="presentation">' +
                        '  <span ref="eMenu" class="ag-header-icon ag-header-cell-menu-button"></span>' +
                        '  <div ref="eLabel" class="ag-header-cell-label" role="presentation">' +
                        '    <span ref="eSortOrder" class="ag-header-icon ag-sort-order" ></span>' +
                        '    <span ref="eSortAsc" class="ag-header-icon ag-sort-ascending-icon" ></span>' +
                        '    <span ref="eSortDesc" class="ag-header-icon ag-sort-descending-icon" ></span>' +
                        '    <span ref="eSortNone" class="ag-header-icon ag-sort-none-icon" ></span>' +
                        '    ** <span ref="eText" class="ag-header-cell-text" role="columnheader"></span>' +
                        '    <span ref="eFilter" class="ag-header-icon ag-filter-icon"></span>' +
                        '  </div>' +
                        '</div>'
    },

    rowData: null,
    enableFilter: true,
    floatingFilter: true,
    enableSorting: true,
    enableColResize: true,
    suppressMenuHide: true
};

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
    var gridDivFullRaport = document.querySelector('#myGridFullRaport');
    new agGrid.Grid(gridDivFullRaport, gridOptionsFullRaport);

    agGrid.simpleHttpRequest({url: '/api/fullraportwaypoints'}).then(function(data) {
        gridOptionsFullRaport.api.setRowData(data);
    });
});