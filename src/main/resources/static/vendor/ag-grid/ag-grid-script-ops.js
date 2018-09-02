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
    var gridDivOps = document.querySelector('#myGridOps');
    new agGrid.Grid(gridDivOps, gridOptionsOps);

    agGrid.simpleHttpRequest({url: '/api/opswaypoints'}).then(function(data) {
        gridOptionsOps.api.setRowData(data);
    });
});