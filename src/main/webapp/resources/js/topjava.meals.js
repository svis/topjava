function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ui/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ui/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });
});